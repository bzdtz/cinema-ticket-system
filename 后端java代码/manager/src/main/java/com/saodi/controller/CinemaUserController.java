package com.saodi.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.ramostear.captcha.HappyCaptcha;
import com.ramostear.captcha.support.CaptchaType;
import com.saodi.po.Cinema;
import com.saodi.po.CinemaUser;
import com.saodi.po.Movie;
import com.saodi.po.MovieCinemaMapping;
import com.saodi.query.CinemaQuery;
import com.saodi.query.CinemaUserQuery;
import com.saodi.service.ICinemaUserService;
import com.saodi.service.IMovieCinemaMappingService;
import com.saodi.service.IMovieService;
import com.saodi.util.TokenUtil;
import com.saodi.vo.LogUserVo;
import com.saodi.util.QiniuConfig;
import com.saodi.vo.ResponseObj;
import com.saodi.vo.SetPassword;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author saodi
 * @since 2023-12-19
 */
@RestController
@RequestMapping("/cinema-user")
public class CinemaUserController {

    @Autowired
    ICinemaUserService cinemaUserService;
    @Autowired
    QiniuConfig qiniu;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    IMovieCinemaMappingService movieCinemaMappingService;
    @Autowired
    IMovieService movieService;

    /**
     *
     * @param cinemaUser
     * @return
     */
    @PostMapping("/saveOrUpdate")
    public  ResponseObj saveOrUpdate(@RequestBody CinemaUser cinemaUser){
        if (!StringUtils.hasText(cinemaUser.getPassword())){
            cinemaUser.setPassword("10086A");
        }
        String encode = passwordEncoder.encode(cinemaUser.getPassword());
        cinemaUser.setPassword(encode);
        if (cinemaUser.getId()==null)
        {
            cinemaUser.setStatus("1");
        }


        if (cinemaUser.getAccount()==null)
        {
//            String substring = UUID.randomUUID().toString().substring(0, 5);
//
//            cinemaUser.setAccount(Integer.parseInt(substring));

            Integer account=(int)((Math.random()*9+1)*100000);
            cinemaUser.setAccount(account);

        }
        boolean saveOrUpdate = cinemaUserService.saveOrUpdate(cinemaUser);
//        movie account 映射 暂定
        QueryWrapper<Movie> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id");
        List<Object> list = movieService.listObjs(queryWrapper);
        List<MovieCinemaMapping> movieCinemaMappings = new LinkedList<>();
        list.forEach(e ->{
            movieCinemaMappings.add(new MovieCinemaMapping((Integer) e,cinemaUser.getAccount()));
        });
        boolean b = movieCinemaMappingService.saveBatch(movieCinemaMappings);

        return  saveOrUpdate?ResponseObj.SUCCESS():ResponseObj.ERROR(2001,"创建或更新失败");
    }


    @DeleteMapping("/{id}")
    public ResponseObj delete(@PathVariable("id") Integer id){
        boolean b = cinemaUserService.removeById(id);
        return b?ResponseObj.SUCCESS():ResponseObj.ERROR(2002,"删除员工信息失败");
    }

    @PostMapping("/set")
    public ResponseObj set(@RequestBody CinemaUser cinemaUser){
        boolean b = cinemaUserService.updateById(cinemaUser);
        return  b?ResponseObj.SUCCESS():ResponseObj.ERROR(2003,"信息修改失败");
    }

    @GetMapping("/{id}")
    public ResponseObj getById(@PathVariable("id") Integer id){
        CinemaUser byId = cinemaUserService.getById(id);
        if (byId == null){
            return ResponseObj.ERROR(2004,"未找到该角色");
        }
        byId.setPassword(null);
        byId.setSalt(null);
        return ResponseObj.SUCCESS(byId);
    }
    @GetMapping("/getByAccount/{account}")
    public ResponseObj getByAccount(@PathVariable("account") Integer account){
        CinemaUser byId = cinemaUserService.getOne(new QueryWrapper<CinemaUser>().eq("account",account));
        if (byId == null){
            return ResponseObj.ERROR(2004,"未找到该角色");
        }
        byId.setPassword(null);
        return ResponseObj.SUCCESS(byId);
    }

    @PostMapping("/page")
    public ResponseObj getByPage(@RequestBody CinemaUserQuery query){
        Page<CinemaUser> page = cinemaUserService.getByPage(query);
        page.getRecords().forEach(u -> u.setPassword(null));
        return ResponseObj.SUCCESS(page);
    }



    @PostMapping("/login")
    public ResponseObj login(@RequestBody LogUserVo logUserVo, HttpSession session, HttpServletRequest request){

        System.out.println(logUserVo);
        boolean flag = HappyCaptcha.verification(request, logUserVo.getCaptcha(), true);

        System.out.println(flag);


        if(!flag){
            return ResponseObj.ERROR(503,"验证码错误");
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("account",logUserVo.getAccount());
        CinemaUser user = cinemaUserService.getOne(queryWrapper);
        if (user == null){
            return ResponseObj.ERROR(502,"用户名不存在");
        }
        if ("0".equals(user.getStatus()))
        {
            return ResponseObj.ERROR(504,"该账号被禁用");
        }

        boolean matches = passwordEncoder.matches(logUserVo.getPassword(),user.getPassword());

        if(matches){

            String token = TokenUtil.sign(String.valueOf(user.getAccount()));

            Map result = new HashMap<>();
            user.setPassword(null);
            result.put("loginUser",user);
            result.put("token",token);
            // Redis 挂了 / 口令不对时，原来会抛 RedisConnectionException → HTTP 500，
            // 登录框只显示"服务器内部错误"，看不出是会话存储的问题。
            try {
                redisTemplate.opsForValue().set(token,user,30, TimeUnit.MINUTES);
            } catch (Exception e) {
                System.out.println("Redis 会话写入失败：" + e.getClass().getSimpleName() + " / " + e.getMessage());
                return ResponseObj.ERROR(517,"会话存储(Redis)不可用，请检查 redis 服务与 spring.redis.password 配置");
            }
            return ResponseObj.SUCCESS(result);
        }
        return ResponseObj.ERROR(501,"密码错误");
    }
    @GetMapping("/captcha")
    public void happyCaptcha(HttpServletRequest request, HttpServletResponse response) {
        HappyCaptcha.require(request, response).type(CaptchaType.WORD_NUMBER_LOWER).build().finish();
    }

    @PostMapping("/setpwd")
    public ResponseObj setPwd(@RequestBody SetPassword setPasswordData,HttpServletRequest request){

        if (!(StringUtils.hasText(setPasswordData.getNewpwd())&&StringUtils.hasText(setPasswordData.getOldpwd()))){
            return ResponseObj.ERROR(508,"信息不能为空");
        }
        String token = request.getHeader("token");
        if (token!=null && !"".equals(token) && TokenUtil.verify(token)){
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("account",TokenUtil.decode(token));
            CinemaUser user = cinemaUserService.getOne(queryWrapper);
            if (user!=null){
                boolean matches = passwordEncoder.matches(setPasswordData.getOldpwd(),user.getPassword());
                if(matches){
                    String encode = passwordEncoder.encode(setPasswordData.getNewpwd());
                    user.setPassword(encode);
                    boolean saveOrUpdate = cinemaUserService.saveOrUpdate(user);
                    return saveOrUpdate?ResponseObj.SUCCESS("修改成功"):ResponseObj.ERROR(509,"修改失败");
                }
                // 原来这里是 return ResponseObj.SUCCESS(user);
                // 旧密码填错会走到这一行：既告诉前端"成功"，又把整个 user 对象
                // （含 60 位 bcrypt password 和 salt）序列化回浏览器。
                return ResponseObj.ERROR(506,"原密码不正确");
            }
             return  ResponseObj.ERROR(507,"登录异常");
        }
        return  ResponseObj.ERROR(507,"登录异常");

    }



    @PostMapping("/updateImg/{id}")
    public ResponseObj multipartFileTest(@ApiParam(value = "multipartFile") @RequestParam MultipartFile multipartFile, @PathVariable("id") Integer id) throws Exception {


        if (!qiniu.isConfigured()) {
            return ResponseObj.ERROR(511, "未配置七牛云密钥（QINIU_ACCESS_KEY / QINIU_SECRET_KEY），图片上传暂不可用");
        }
        Configuration cfg = new Configuration(Region.huabei());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        UploadManager uploadManager = new UploadManager(cfg);
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + multipartFile.getOriginalFilename();
        Auth auth = Auth.create(qiniu.getAccessKey(), qiniu.getSecretKey());
        String upToken = auth.uploadToken(qiniu.getBucket());
        try {
            Response response = uploadManager.put(multipartFile.getInputStream(), fileName, upToken, null, null);
            //TODO 判断是否存储成功
            if (true) {
                UpdateWrapper uw = new UpdateWrapper();
                uw.eq("id", id);
                uw.set("salt", qiniu.getDomain() + "/" + fileName);
                boolean update = cinemaUserService.update(uw);
                return update ? ResponseObj.SUCCESS("上传成功", qiniu.getDomain() + "/" + fileName) : ResponseObj.ERROR(510, "上传失败");
            }
        } catch (Exception ex) {
            return ResponseObj.ERROR();
        }
        return ResponseObj.ERROR();
//        File file = new File("C:\\Users\\SAODI\\Desktop\\1219\\html\\1.png");
//        System.out.println(multipartFile.getName());
//        System.out.println(multipartFile.getOriginalFilename());
//        System.out.println(multipartFile.getContentType());
//
//        multipartFile.transferTo(file);
//        System.out.println(file.getAbsolutePath());
//        return file.getAbsolutePath();
    }

    @PostMapping("/delect")
    public  ResponseObj delect(@RequestBody CinemaUser cinemaUser){
        boolean b = cinemaUserService.removeById(cinemaUser);
        return  b?ResponseObj.SUCCESS("删除成功"):ResponseObj.ERROR();
    }

    @PostMapping("/reset/{id}")
    public  ResponseObj reset(@PathVariable("id") Integer id){
        CinemaUser byId = cinemaUserService.getById(id);
        byId.setPassword("10086A");
        String encode = passwordEncoder.encode(byId.getPassword());
        byId.setPassword(encode);
        UpdateWrapper<CinemaUser> id1 = new UpdateWrapper<CinemaUser>(new CinemaUser()).eq("id", id);
        id1.set("password",encode);
        boolean saveOrUpdate = cinemaUserService.update(id1);
        return saveOrUpdate?ResponseObj.SUCCESS():ResponseObj.ERROR();
    }

    @GetMapping("/rePassword")
    public ResponseObj rePwd(HttpServletRequest request) {
        String token = request.getHeader("token");

        if (token != null && !"".equals(token) && TokenUtil.verify(token)) {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("account", TokenUtil.decode(token));
            CinemaUser user = cinemaUserService.getOne(queryWrapper);

            if (user != null) {
                // 在这里设置一个固定的密码，例如 "resetPassword123"
                String resetPassword = "111111";
                String encode = passwordEncoder.encode(resetPassword);
                user.setPassword(encode);

                // 保存或更新用户信息
                boolean saveOrUpdate = cinemaUserService.saveOrUpdate(user);

                return saveOrUpdate ? ResponseObj.SUCCESS("密码重置成功") : ResponseObj.ERROR();
            }

            return ResponseObj.ERROR(507, "登录异常");
        }

        return ResponseObj.ERROR(507, "登录异常");
    }


    @ApiOperation("根据名称获取管理员account")
    @PostMapping("/getByPage")
    public ResponseObj page(@RequestBody  CinemaUserQuery query) {
        Page<CinemaUser> page = cinemaUserService.getByPage(query);
        return ResponseObj.SUCCESS(page);
    }


}

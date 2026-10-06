package com.saodi.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.ramostear.captcha.HappyCaptcha;
import com.ramostear.captcha.support.CaptchaType;
import com.saodi.po.*;
import com.saodi.service.*;
import com.saodi.util.CurrentUser;
import com.saodi.util.QiniuConfig;
import com.saodi.util.TokenUtil;
import com.saodi.vo.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author saodi
 * @since 2023-12-19
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserService service;
    @Autowired
    QiniuConfig qiniu;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    IMovieService movieService;
    @Autowired
    IOrderService orderService;
    @Autowired
    IOrderDetailService orderDetailService;

    /**
     * 路径上的 userId 必须是 token 持有者本人。校验通过返回 null。
     */
    private ResponseObj checkSelf(HttpServletRequest request, Integer userId) {
        Integer current = CurrentUser.id(request);
        if (current == null) {
            return ResponseObj.ERROR(501, "登录验证失败");
        }
        if (!current.equals(userId)) {
            return ResponseObj.ERROR(510, "无权访问该资源");
        }
        return null;
    }

    /**
     * 订单归属校验：orderId 对应的订单必须属于 token 持有者。校验通过返回 null。
     */
    private ResponseObj checkOrderOwner(HttpServletRequest request, Integer orderId) {
        Integer current = CurrentUser.id(request);
        if (current == null) {
            return ResponseObj.ERROR(501, "登录验证失败");
        }
        Order order = orderService.getOne(new QueryWrapper<Order>().eq("order_id", orderId));
        if (order == null) {
            return ResponseObj.ERROR(502, "订单不存在");
        }
        if (!current.equals(order.getUserId())) {
            return ResponseObj.ERROR(510, "无权访问该订单");
        }
        return null;
    }

    @PostMapping("/login")
    public ResponseObj login(@RequestBody LoginUserVo logUserVo, HttpSession session, HttpServletRequest request) {

        System.out.println(logUserVo);
        boolean flag = HappyCaptcha.verification(request, logUserVo.getCaptcha(), true);

        System.out.println(flag);


        if(!flag){
            return ResponseObj.ERROR(503,"验证码错误");
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", logUserVo.getId());
        User user = service.getOne(queryWrapper);
        if (user != null) {

            boolean matches = passwordEncoder.matches(logUserVo.getPassword(), user.getPassword());

            if (matches) {

                String token = TokenUtil.sign(String.valueOf(user.getId()));

                Map result = new HashMap<>();
                user.setPassword(null);
                user.setSalt(null);
                result.put("loginUser", user);
                result.put("token", token);

                return ResponseObj.SUCCESS(result);
            }
            return ResponseObj.ERROR(501, "密码错误");
        }
        return ResponseObj.ERROR(502, "用户名不存在");
    }

    @GetMapping("/captcha")
    public void happyCaptcha(HttpServletRequest request, HttpServletResponse response) {
        HappyCaptcha.require(request, response).type(CaptchaType.WORD_NUMBER_LOWER).build().finish();
    }

    @PostMapping("/setpwd")
    public ResponseObj setPwd(@RequestBody SetPassword setPasswordData, HttpServletRequest request) {

        if (!(StringUtils.hasText(setPasswordData.getNewpwd()) && StringUtils.hasText(setPasswordData.getOldpwd()))) {
            return ResponseObj.ERROR(508, "信息不能为空");
        }
        String token = request.getHeader("token");
        if (token != null && !"".equals(token) && TokenUtil.verify(token)) {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("id", TokenUtil.decode(token));
            User user = service.getOne(queryWrapper);
            if (user != null) {
                boolean matches = passwordEncoder.matches(setPasswordData.getOldpwd(), user.getPassword());
                if (matches) {
                    String encode = passwordEncoder.encode(setPasswordData.getNewpwd());
                    user.setPassword(encode);
                    boolean saveOrUpdate = service.saveOrUpdate(user);
                    return saveOrUpdate ? ResponseObj.SUCCESS("修改成功") : ResponseObj.ERROR(509, "修改失败");
                }
                // 原来这里是 return ResponseObj.SUCCESS(user);
                // 旧密码填错会走到这一行：既告诉前端"成功"，又把整个 user 对象
                // （含 bcrypt password 和 salt）序列化回浏览器。
                return ResponseObj.ERROR(506, "原密码不正确");
            }
            return ResponseObj.ERROR(507, "登录异常");
        }
        return ResponseObj.ERROR(507, "登录异常");

    }

    @PostMapping("/register")
    public ResponseObj register(@RequestBody RegisterUserVo registerUserVo, HttpServletRequest request) {
        // 假设 RegisterUserVo 包含诸如 username、password 等必要信息的字段
        System.out.println(registerUserVo);

        // 检查账号是否已经被占用
        QueryWrapper usernameQueryWrapper = new QueryWrapper();
        usernameQueryWrapper.eq("id", registerUserVo.getId());
        User existingUser = service.getOne(usernameQueryWrapper);
        if (existingUser != null) {
            return ResponseObj.ERROR(503, "账号已存在");
        }

        // 如果需要，验证验证码
        // boolean flag = HappyCaptcha.verification(request, registerUserVo.getCaptcha(), true);
        // System.out.println(flag);
        // if (!flag) {
        //     return ResponseObj.ERROR(504, "验证码验证失败");
        // }

        // 创建新用户
        User newUser = new User();
        newUser.setUserName(registerUserVo.getUsername());
        newUser.setPassword(passwordEncoder.encode(registerUserVo.getPassword()));
        // 根据需要设置其他用户属性
        newUser.setPhone(registerUserVo.getPhone());
        newUser.setId(registerUserVo.getId());
        newUser.setStatus(1+"");

        // 在接收到请求时，创建一个当前时间
        Date currentTime = new Date();
        // 使用SimpleDateFormat将日期时间格式化为 "yyyy-MM-dd hh:mm:ss" 形式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String formattedDateTime = dateFormat.format(currentTime);

        try {
            Date formattedDateTimeObject = dateFormat.parse(formattedDateTime);
            newUser.setCreatetime(formattedDateTimeObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 将新用户保存到数据库
        boolean saved = service.save(newUser);
        if (saved) {
            // 您可以选择生成并返回新用户的令牌
            // String token = TokenUtil.sign(String.valueOf(newUser.getId));

            // 从响应中省略敏感信息
            newUser.setPassword(null);
            newUser.setSalt(null);

            return ResponseObj.SUCCESS(newUser);
        } else {
            return ResponseObj.ERROR(505, "注册失败");
        }
    }


    @GetMapping("/getInfo/{id}")
    public ResponseObj getInfo(@PathVariable("id") Integer id, HttpServletRequest request) {
        ResponseObj deny = checkSelf(request, id);
        if (deny != null) {
            return deny;
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        User user = service.getOne(queryWrapper);
        if (user != null) {
            user.setPassword(null);
        }
        return ResponseObj.SUCCESS("success", user);
    }

    @PostMapping("/updateInfo")
    public ResponseObj updateInfo(@RequestBody User user, HttpServletRequest request) {
        ResponseObj deny = checkSelf(request, user.getId());
        if (deny != null) {
            return deny;
        }
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper.eq("id", user.getId());
        userUpdateWrapper.set(StringUtils.hasText( user.getUserName()),"User_Name",user.getUserName());
        userUpdateWrapper.set(user.getAge()!=null,"age",user.getAge());
        userUpdateWrapper.set(StringUtils.hasText( user.getEmail()),"email",user.getEmail());
        userUpdateWrapper.set(StringUtils.hasText( user.getPhone()),"phone",user.getPhone());

        boolean b = service.update(userUpdateWrapper);
        return b ? ResponseObj.SUCCESS("success") : ResponseObj.ERROR(500, "修改失败");
    }

    @PostMapping("/getHistory/{id}")
    public ResponseObj getHistory(@PathVariable("id") Integer id, HttpServletRequest request) {
        ResponseObj deny = checkSelf(request, id);
        if (deny != null) {
            return deny;
        }
        List<Integer> history = service.getHistory(id);

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.in("id",history);
        List<Cinema> list = movieService.list(queryWrapper);

        return history != null ? ResponseObj.SUCCESS(list) : ResponseObj.ERROR();
    }

    @PostMapping("/updateImg/{id}")
    public ResponseObj multipartFileTest(@ApiParam(value = "multipartFile") @RequestParam MultipartFile multipartFile, @PathVariable("id") Integer id, HttpServletRequest request) throws Exception {

        ResponseObj deny = checkSelf(request, id);
        if (deny != null) {
            return deny;
        }

        if (!qiniu.isConfigured()) {
            return ResponseObj.ERROR(511, "未配置七牛云上传参数，缺少 " + qiniu.missingVars() + "，图片上传暂不可用");
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
                uw.set("head_img", qiniu.getDomain() + "/" + fileName);
                boolean update = service.update(uw);
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

    @PostMapping("/getOrder/{id}")
    public ResponseObj getOrder(@PathVariable("id") Integer id, HttpServletRequest request){

        ResponseObj deny = checkSelf(request, id);
        if (deny != null) {
            return deny;
        }

        List<Order> order = orderService.getOrder(id);
        return order!=null?ResponseObj.SUCCESS(order):ResponseObj.ERROR();
    }
    @PostMapping("/getOrderDetail/{orderId}")
    public  ResponseObj getOrderDetail(@PathVariable("orderId") Integer id, HttpServletRequest request){
        ResponseObj deny = checkOrderOwner(request, id);
        if (deny != null) {
            return deny;
        }
        QueryWrapper<OrderDetail> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq(id!=null,"order_id",id);
        List<OrderDetail> list = orderDetailService.list(objectQueryWrapper);
        return list!=null?ResponseObj.SUCCESS(list):ResponseObj.ERROR();
    }

    @ApiOperation("根据")
    @GetMapping("/getStatus/{id}")
    public  ResponseObj  getStatus(@PathVariable("id")Integer userId, HttpServletRequest request)
    {
        ResponseObj deny = checkSelf(request, userId);
        if (deny != null) {
            return deny;
        }
        User user = service.getById(userId);
        if (user == null) {
            return ResponseObj.ERROR(502, "用户名不存在");
        }
        if ("1".equals(user.getStatus()))
        {
            return ResponseObj.SUCCESS("ok");
        }
        else {
            return ResponseObj.ERROR(500,"error");
        }
    }

}

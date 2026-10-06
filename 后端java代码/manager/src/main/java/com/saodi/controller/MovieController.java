package com.saodi.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.saodi.po.Movie;
import com.saodi.query.MovieQuery;
import com.saodi.service.IMovieService;
import com.saodi.vo.PageBean;
import com.saodi.util.QiniuConfig;
import com.saodi.vo.ResponseObj;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author saodi
 * @since 2023-12-19
 */
@RestController
@RequestMapping("/movie")
@Api(tags = "后台管理员进行的电影的CRUD", description = "")
public class MovieController {

    @Autowired
    IMovieService service;
    @Autowired
    QiniuConfig qiniu;

    @ApiOperation("无id进行添加，有id进行修改")
    @PostMapping("/addorupdate")
    public ResponseObj addOrUpdate(@RequestBody Movie movie) {
        System.out.println(movie);
        if (movie == null) {
            return ResponseObj.ERROR(500, "添加或者修改");
        }
        if (movie.getId() != null) {
            //有id 执行的是修改的操作
            QueryWrapper<Movie> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", movie.getId());
            boolean update = service.update(movie, queryWrapper);
            return update ? ResponseObj.SUCCESS("修改成功") : ResponseObj.ERROR(501, "修改失败");
        }

        boolean save = service.save(movie);
        return save ? ResponseObj.SUCCESS("添加成功", movie.getId()) : ResponseObj.ERROR(500, "添加失败");


    }

    @ApiOperation("根据movieId进行删除")
    @PostMapping("/delmovie/{id}")
    public ResponseObj delById(@PathVariable("id") Integer movieId) {
        boolean b = service.removeById(movieId);
        return b ? ResponseObj.SUCCESS("删除成功") : ResponseObj.ERROR(500, "删除失败");
    }

    @ApiOperation("根据  movieId  区域  上映时间  进行条件搜索  搜索条件为空 则无搜索条件，搜索条件不为空则搜索条件起作用")
    @PostMapping("/page")
    public PageBean getByPage(@RequestBody MovieQuery query) {
        if (query.getTypeId() == null) {
            query.setTypeId(1);
        }
        if (query.getRegion() == null) {
            query.setRegion("全部");
        }
        System.out.println("=============================================");
        System.out.println(query);
        PageBean<Movie> pageBean = service.getPage(query);

        System.out.println("++++++++++++++++++++++++++++++++++++++");
        System.out.println(pageBean);
        return pageBean;
    }


    @ApiOperation("根据id批量删除电影数据")
    @PostMapping("/batch")
    public ResponseObj delBatch(@RequestBody List<Integer> ids) {
        boolean b = service.removeBatchByIds(ids);
        return b ? ResponseObj.SUCCESS("删除成功") : ResponseObj.ERROR(500, "删除失败");
    }

    @PostMapping("/getByAccount/{account}")
    public ResponseObj getByAccount(@PathVariable("account") Integer account) {
        List<Movie> byAccount = service.getByAccount(account);
        return byAccount != null ? ResponseObj.SUCCESS(byAccount) : ResponseObj.ERROR();
    }


    @PostMapping("/getType")
    public ResponseObj getMovieByTypeId(@RequestBody MovieQuery movieQuery) {

        PageBean<Movie> page = service.getPage(movieQuery);
        System.out.println(page.getData());
        return ResponseObj.SUCCESS("查询成功", page);
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
                uw.set("banner", qiniu.getDomain() + "/" + fileName);
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

    @ApiOperation("表：movie，movieType  movie_type_mapping   根据movieId 返回对应的类型")
    @GetMapping("/type/{id}")
    public  ResponseObj  getTypeById(@PathVariable("id") Integer movieId)
    {
        List<String> types = service.getTypeById(movieId);
        return ResponseObj.SUCCESS("查询成功",types);
    }



    @GetMapping("/getMovie/{id}")
    public   ResponseObj   getMovieById(@PathVariable("id") Integer movieId)
    {

        QueryWrapper<Movie>   queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",movieId);
        Movie movie = service.getOne(queryWrapper);
        if (movie.getReleaseTime()==null)
        {
             LocalDateTime localDateTime = LocalDateTime.of(1970, 1, 1, 0, 0);
            Date releaseDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
            movie.setReleaseTime(releaseDate);
        }
        return ResponseObj.SUCCESS("success",movie);
    }
}

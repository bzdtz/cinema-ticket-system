package com.saodi.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.saodi.po.Cinema;
import com.saodi.po.Permission;
import com.saodi.query.CinemaQuery;
import com.saodi.query.PermissionQuery;
import com.saodi.service.ICinemaService;
import com.saodi.vo.CinemaVo;
import com.saodi.util.QiniuConfig;
import com.saodi.vo.ResponseObj;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author saodi
 * @since 2023-12-19
 */
@RestController
@RequestMapping("/cinema")
public class CinemaController {

    @Autowired
    ICinemaService service;
    @Autowired
    QiniuConfig qiniu;

    @PostMapping("/getByPage")
    public ResponseObj page(@RequestBody CinemaQuery query) {
        Page<Cinema> page = service.getByPage(query);
        return ResponseObj.SUCCESS(page);
    }

    @PostMapping("/addCinema")
    public ResponseObj addCinema(@RequestBody Cinema cinema) {
        boolean save = service.save(cinema);
        return save ? ResponseObj.SUCCESS() : ResponseObj.ERROR();
    }

    @PostMapping("/getByAccount")
    public ResponseObj getByAccount(@RequestBody CinemaVo cinemaVo) {
        QueryWrapper<Cinema> account = new QueryWrapper<Cinema>(new Cinema()).eq(cinemaVo.getCinemaUserId() != null && cinemaVo.getAccount() != null, "account", cinemaVo.getAccount());

        List<Cinema> list = service.list(account);
        return list != null ? ResponseObj.SUCCESS(list) : ResponseObj.ERROR();
    }


    @PostMapping("/updateImg/{id}")
    public ResponseObj multipartFileTest(@ApiParam(value = "multipartFile") @RequestParam MultipartFile multipartFile, @PathVariable("id") Integer id) throws Exception {


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
                uw.set("img", qiniu.getDomain() + "/" + fileName);
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


    @ApiOperation("根据影院id删除数据")
    @GetMapping("del/{id}")
    public ResponseObj delById(@PathVariable("id") Integer cinemaId) {
        boolean b = service.removeById(cinemaId);
        return b ? ResponseObj.SUCCESS("success") : ResponseObj.ERROR(500, "error");
    }

    @ApiOperation("根据影院id删除数据")
    @PostMapping("/delBatch")
    public ResponseObj delById(@RequestBody Collection<Integer> cinemaIds) {

        boolean b = service.removeBatchByIds(cinemaIds);
        return b ? ResponseObj.SUCCESS("success") : ResponseObj.ERROR(500, "error");
    }

    @ApiOperation("修改影院")
    @PostMapping("/update")
    public ResponseObj updateCinema(@RequestBody Cinema cinema) {
        QueryWrapper<Cinema> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",cinema.getId());
        boolean b = service.update(cinema,queryWrapper);
        return b ? ResponseObj.SUCCESS("success") : ResponseObj.ERROR(500, "error");
    }






}

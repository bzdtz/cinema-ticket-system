package com.saodi.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.saodi.po.Permission;
import com.saodi.po.User;
import com.saodi.query.UserQuery;
import com.saodi.service.IUserService;
import com.saodi.util.QiniuConfig;
import com.saodi.vo.PageBean;
import com.saodi.vo.ResponseObj;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserService service;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    QiniuConfig qiniu;

    @ApiOperation("管理员进行用户列表的信息获取  姓名模糊查询   手机号精确查询")
    @PostMapping("/page")
    public ResponseObj getPage(@RequestBody UserQuery userQuery)
    {
        Page<User> byPage = service.getByPage(userQuery);
        return byPage!=null?ResponseObj.SUCCESS(byPage):ResponseObj.ERROR();
    }

    @PostMapping("/updateImg/{id}")
    public ResponseObj multipartFileTest(@ApiParam(value = "multipartFile") @RequestParam MultipartFile multipartFile,@PathVariable("id") Integer id) throws Exception {


        if (!qiniu.isConfigured()) {
            return ResponseObj.ERROR(511, "未配置七牛云上传参数，缺少 " + qiniu.missingVars() + "，头像上传暂不可用");
        }
        Configuration cfg = new Configuration(Region.huabei());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        UploadManager uploadManager = new UploadManager(cfg);
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + multipartFile.getOriginalFilename();
        String fileUrl = qiniu.getDomain() + "/" + fileName;
        Auth auth = Auth.create(qiniu.getAccessKey(), qiniu.getSecretKey());
        String upToken = auth.uploadToken(qiniu.getBucket());
        try {
            Response response = uploadManager.put(multipartFile.getInputStream(), fileName, upToken, null, null);
            //TODO 判断是否存储成功
            if (true) {
                UpdateWrapper uw = new UpdateWrapper();
                uw.eq("id", id);
                uw.set("head_img", fileUrl);
                boolean update = service.update(uw);
                return update ? ResponseObj.SUCCESS("上传成功", fileUrl) : ResponseObj.ERROR(510, "上传失败");
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

    @PostMapping("/saveOrUpdate")
    public  ResponseObj saveOrUpdate(@RequestBody User user){
        if (!StringUtils.hasText(user.getPassword())){
            user.setPassword("@10086A");
        }
        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        boolean b = service.saveOrUpdate(user);
        return  b?ResponseObj.SUCCESS("添加成功"):ResponseObj.ERROR();
    }
    @PostMapping("/batchupdate")
    public ResponseObj batchUpdate(@RequestBody List<User> list){
        if (list==null){
            return ResponseObj.ERROR();
        }
        list.forEach(System.out::println);
        boolean b = service.updateBatchById(list);
        return b?ResponseObj.SUCCESS():ResponseObj.ERROR();
    }


}

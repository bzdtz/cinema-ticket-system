package com.saodi.interceptor;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.saodi.mapper.RolePermissionMapper;
import com.saodi.po.CinemaUser;
import com.saodi.po.Permission;
import com.saodi.po.RolePermission;
import com.saodi.service.IPermissionService;
import com.saodi.util.TokenUtil;
import com.saodi.vo.ResponseObj;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    RolePermissionMapper rolePermissionMapper;

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    IPermissionService permissionService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        String movieMsg = "无";

        /*
        跨域请求有两次：
        第一次是OPTIONS请求   验证是否可以跨域请求  直接放行
        第二次是真正的请求
         */
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        //1 登录拦截
        String token = request.getHeader("token");
        // Redis 的 key 不允许为 null，缺少 token 时直接走未登录分支。
        // 这里必须 try 住：Redis 没起 / 口令不对（application.yml 里 requirepass 与密码不匹配）时
        // Lettuce 抛的是 RedisConnectionException，原来直接冒泡成 HTTP 500，
        // 前端每个菜单都显示"服务器内部错误"，看起来像全站崩掉。
        // 正确表现应该是"按未登录处理"→ code 501 → 前端跳登录页。
        CinemaUser o = null;
        if (token != null && !"".equals(token)) {
            try {
                o = (CinemaUser) redisTemplate.opsForValue().get(token);
            } catch (Exception e) {
                System.out.println("Redis 会话读取失败，按未登录处理：" + e.getClass().getSimpleName()
                        + " / " + e.getMessage());
            }
        }
        // 不打印 token 原文和整个 CinemaUser，日志会进文件/控制台，属于凭据外泄面
        System.out.println("登录拦截：token=" + (token == null ? "无" : "有") + "，会话命中=" + (o != null));
        //用redis后的验证跟redis存在时间有关习
        if (token != null && o != null) {
//            重置redis中等录有效期
            try {
                redisTemplate.opsForValue().set(token, o, 30, TimeUnit.MINUTES);
            } catch (Exception e) {
                System.out.println("Redis 会话续期失败（本次请求仍放行）：" + e.getMessage());
            }
            String servletPath = request.getServletPath();
            Integer account = o.getAccount();
            boolean check = permissionService.Check(account, servletPath);
            if (!check){
                response.setCharacterEncoding("utf-8");
                response.setContentType("application/json; charset=UTF-8");
                PrintWriter writer = response.getWriter();
                // 在外面写好在拷贝进去
                //把一个ResponseObj转成json
                ObjectMapper objectMapper = new ObjectMapper();
                String responseObjJson = objectMapper.writeValueAsString(ResponseObj.ERROR(510, "权限不足"));
                writer.write(responseObjJson);
                writer.close();
                return false;
            }

//        //不为空  且验证通过 直接放行
//        if (token!=null && !"".equals(token) && TokenUtil.verify(token)){
            //校验成功后 进行权限验证
//            String account =TokenUtil.decode(token);
//            System.out.println("account");
//            System.out.println(account);
//            //获取到account
//            List<RolePermission> rolePermissions = rolePermissionMapper.getByRoleId(account);
//            System.out.println("+++++++++====");
//            System.out.println(rolePermissions);
//            //获取到该账户所具有的所有权限  获取权限所有的url
//            List<String> urls=new ArrayList<>();
//            for (RolePermission rolePermission :rolePermissions) {
//                urls.add(rolePermission.getPermission().getUrl());
//            }
//            //获取完成
//            //进行路径的对比
//            String uri = request.getRequestURI();
//            System.out.println("uri+++++++++++++++++");
//            System.out.println(uri);
            //目前针对 movie模块进行权限限制
//            if (!uri.contains("movie"))
//            {
//                //
//                return  true;
//            }
            //判断逻辑：如果当前路径中包含子串则为：当前用户有该权限
//            for (String s :urls) {
//                if (uri.contains(s)){
//                    //条件成立则 有权限  可以进行之后的操作
//                    return true;
//                }
//            }

            //未能返回 证明无权限



//            response.setCharacterEncoding("utf-8");
//            response.setContentType("application/json; charset=UTF-8");
//            PrintWriter writer = response.getWriter();
//            // 在外面写好在拷贝进去
//            //把一个ResponseObj转成json
//            ObjectMapper objectMapper = new ObjectMapper();
//            String responseObjJson = objectMapper.writeValueAsString(ResponseObj.ERROR(501,"501"));
//            writer.write(responseObjJson);
//            writer.close();
//            return false;

            return true;
        }

        //2 权限拦截 //@TODO
        //都是登录是吧返回一个错误 {“success”:false,"message":"noLogin"}，让前端条页面
        //每次请求都会先进行 获取当前的account 通过account来确定当前账户是否有某些权限



        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter writer = response.getWriter();
        // 在外面写好在拷贝进去
        //把一个ResponseObj转成json
        ObjectMapper objectMapper = new ObjectMapper();
        String responseObjJson = objectMapper.writeValueAsString(ResponseObj.ERROR(501, "登录验证失败"));
        writer.write(responseObjJson);
        writer.close();
        return false;
    }
}
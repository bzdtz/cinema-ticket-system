package com.saodi.controller;

import com.saodi.ai.AiAssistantService;
import com.saodi.ai.vo.AiRequest;
import com.saodi.util.CurrentUser;
import com.saodi.vo.ResponseObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  影院智能体入口。
 * </p>
 *
 * @author saodi
 */
@RestController
@RequestMapping("/ai")
public class AiController {

    @Autowired
    private AiAssistantService service;

    /**
     * 挂在 app 侧而不是新开一个服务，是为了直接吃到 WebMvcConfig 里那条 /** 的登录拦截：
     * 没登录问不了，也不用再写一套鉴权。api-key 只留在后端，响应里不会带出去。
     */
    @PostMapping("/chat")
    public ResponseObj chat(@RequestBody AiRequest request, HttpServletRequest servletRequest) {
        // 身份只认登录态：客户端在这个字段里写什么都不算，在这里统一覆盖掉
        request.setUserId(CurrentUser.id(servletRequest));
        return ResponseObj.SUCCESS(service.chat(request));
    }
}

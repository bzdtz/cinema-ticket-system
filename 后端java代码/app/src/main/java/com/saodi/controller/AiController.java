package com.saodi.controller;

import com.saodi.ai.AiAssistantService;
import com.saodi.ai.vo.AiRequest;
import com.saodi.vo.ResponseObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseObj chat(@RequestBody AiRequest request) {
        return ResponseObj.SUCCESS(service.chat(request));
    }
}

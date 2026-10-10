package com.saodi.controller;

import com.saodi.service.HotMovieSyncService;
import com.saodi.vo.ResponseObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  外部热度榜：读快照、刷新快照。
 * </p>
 *
 * @author saodi
 */
@RestController
@RequestMapping("/hot")
public class HotController {

    @Autowired
    private HotMovieSyncService service;

    /**
     * 读的是库里最近一批快照，不打外部接口，所以这个接口不会因为榜单源挂了而变慢。
     * 里面带了 fetchedAt：热度是有时效的，不给时间的榜单只能当没有。
     */
    @GetMapping("/list")
    public ResponseObj list() {
        return ResponseObj.SUCCESS(service.current());
    }

    /**
     * 手动刷一次。放在登录拦截后面（/hot/** 不在放行名单里），所以它是登录用户点出来的动作，
     * 不是一个谁都能拿来打外部接口的开放代理。
     */
    @PostMapping("/refresh")
    public ResponseObj refresh(@RequestParam(defaultValue = "20") int limit) {
        return ResponseObj.SUCCESS(service.refresh(limit));
    }

    /**
     * 把榜单前几部站内还没有的片补成影片，并排出未来一周场次。
     * 只有点这一句才会改到业务表：刷新榜单不自动补片，因为排片表是要卖票的，
     * 不能跟着上游的日榜天天变。
     */
    @PostMapping("/promote")
    public ResponseObj promote(@RequestParam(defaultValue = "5") int limit) {
        return ResponseObj.SUCCESS(service.promote(limit));
    }
}

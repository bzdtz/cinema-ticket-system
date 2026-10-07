package com.saodi.ai;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  草稿确认令牌。签发时把草稿内容钉在 Redis 里，随 HTTP 响应给浏览器，
 *  但喂回模型的那份 tool 结果里已经把这枚令牌剥掉了——
 *  所以下单接口收到令牌就等价于"有个活人点了确认"，
 *  这是结构上的限制，不是提示词里的一句嘱咐。
 * </p>
 *
 * @author saodi
 */
@Component
public class DraftTokenStore {

    private static final String PREFIX = "ai:draft:";

    /** Redis 没通也不该影响主流程，所以不做强依赖 */
    @Autowired(required = false)
    private StringRedisTemplate redis;

    @Autowired
    private AiProperties props;

    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * 签发失败返回 null：草稿照常给前端，只是这一单退回"手动选座"路径。
     * 不因为凭证发不出来就把下单入口堵掉。
     */
    public String issue(DraftTicket ticket) {
        if (redis == null || ticket == null || ticket.getUserId() == null) {
            return null;
        }
        try {
            String token = UUID.randomUUID().toString().replace("-", "");
            redis.opsForValue().set(PREFIX + token, mapper.writeValueAsString(ticket),
                    props.getDraftTtlSeconds(), TimeUnit.SECONDS);
            return token;
        } catch (Exception e) {
            return null;
        }
    }

    public DraftTicket find(String token) {
        if (redis == null || token == null || token.trim().isEmpty()) {
            return null;
        }
        try {
            String payload = redis.opsForValue().get(PREFIX + token);
            return payload == null ? null : mapper.readValue(payload, DraftTicket.class);
        } catch (Exception e) {
            return null;
        }
    }

    /** 用完即销毁，同一枚令牌不能下两单 */
    public void consume(String token) {
        if (redis == null || token == null) {
            return;
        }
        try {
            redis.delete(PREFIX + token);
        } catch (Exception ignored) {
            // 没删掉也只是多活一会儿，TTL 到点自己走
        }
    }

    public int ttlSeconds() {
        return props.getDraftTtlSeconds();
    }
}

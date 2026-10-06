package com.saodi.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 七牛云上传配置。
 * <p>
 * 之前 accessKey / secretKey 以明文常量抄在 5 个 controller 里
 * （app/UserController、manager 的 Cinema / CinemaUser / Movie / User），
 * 源码本身就算泄漏，任何拿到代码的人都能直接往 saodi-movie 这个桶里写文件。
 * 现在统一挪到 application.yml，并且默认走环境变量：
 * <pre>
 *   set QINIU_ACCESS_KEY=xxx
 *   set QINIU_SECRET_KEY=yyy
 * </pre>
 * 没配密钥时上传接口直接返回明确错误，而不是拿着泄露的旧 key 去请求。
 */
@Component
public class QiniuConfig {

    @Value("${qiniu.access-key:}")
    private String accessKey;

    @Value("${qiniu.secret-key:}")
    private String secretKey;

    @Value("${qiniu.bucket:saodi-movie}")
    private String bucket;

    /** 桶的访问域名，拼在文件名前面返回给前端 */
    @Value("${qiniu.domain:http://s6gtr7r2k.hb-bkt.clouddn.com}")
    private String domain;

    public String getAccessKey() {
        return accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getBucket() {
        return bucket;
    }

    /** 去掉末尾斜杠，调用方自己拼 "/" + fileName */
    public String getDomain() {
        if (domain != null && domain.endsWith("/")) {
            return domain.substring(0, domain.length() - 1);
        }
        return domain;
    }

    public boolean isConfigured() {
        return StringUtils.hasText(accessKey) && StringUtils.hasText(secretKey)
                && StringUtils.hasText(bucket);
    }

    /** 只在日志里出现 key 的前 4 位，绝不打印完整密钥 */
    public String safeDescribe() {
        return "bucket=" + bucket + ", accessKey=" + (StringUtils.hasText(accessKey)
                ? accessKey.substring(0, Math.min(4, accessKey.length())) + "***(已配置)"
                : "未配置");
    }
}

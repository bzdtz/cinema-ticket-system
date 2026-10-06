package com.saodi.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 七牛云上传配置。
 * <p>
 * 之前 accessKey / secretKey 以明文常量抄在 5 个 controller 里
 * （app/UserController、manager 的 Cinema / CinemaUser / Movie / User），
 * 源码本身就算泄漏，任何拿到代码的人都能直接往那个桶里写文件。
 * 现在统一挪到 application.yml，四项全部只从环境变量来，仓库里不留任何默认值：
 * <pre>
 *   set QINIU_ACCESS_KEY=xxx
 *   set QINIU_SECRET_KEY=yyy
 *   set QINIU_BUCKET=你的桶名
 *   set QINIU_DOMAIN=桶的访问域名
 * </pre>
 * 没配齐时上传接口直接返回明确错误（并说清缺哪几个），而不是拿着别人的 key 去请求。
 */
@Component
public class QiniuConfig {

    @Value("${qiniu.access-key:}")
    private String accessKey;

    @Value("${qiniu.secret-key:}")
    private String secretKey;

    @Value("${qiniu.bucket:}")
    private String bucket;

    /** 桶的访问域名，拼在文件名前面返回给前端 */
    @Value("${qiniu.domain:}")
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

    /**
     * 缺哪几个环境变量。bucket 现在也没有默认值了，所以光提示 key 会让人以为
     * 配了密钥就够 —— 这里把三项一起报出来。domain 不在其中：
     * 没有域名只是返回给前端的图片 URL 不完整，不影响上传本身。
     */
    public String missingVars() {
        List<String> missing = new ArrayList<>();
        if (!StringUtils.hasText(accessKey)) missing.add("QINIU_ACCESS_KEY");
        if (!StringUtils.hasText(secretKey)) missing.add("QINIU_SECRET_KEY");
        if (!StringUtils.hasText(bucket)) missing.add("QINIU_BUCKET");
        return String.join(" / ", missing);
    }

    /** 只在日志里出现 key 的前 4 位，绝不打印完整密钥 */
    public String safeDescribe() {
        return "bucket=" + bucket + ", accessKey=" + (StringUtils.hasText(accessKey)
                ? accessKey.substring(0, Math.min(4, accessKey.length())) + "***(已配置)"
                : "未配置");
    }
}

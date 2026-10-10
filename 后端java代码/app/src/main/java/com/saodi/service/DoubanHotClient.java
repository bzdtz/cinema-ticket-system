package com.saodi.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  豆瓣「热门」榜单客户端。挑它是因为这条接口免 key、免登录、返回的就是眼下的片子，
 *  而站内 movie 表的 release_time 最晚停在 2024-02-08 —— 拿它答「最近什么在映」永远慢两年。
 *
 *  它只被刷新逻辑调用，智能体读的是落库后的快照（见 ai-agent 的 hot_now 工具）：
 *  这是个非官方接口，随时会改，所以不能挂在用户请求链路上实时穿透，只能抓一次存一批。
 * </p>
 *
 * @author saodi
 */
@Component
public class DoubanHotClient {

    private static final String ENDPOINT = "https://movie.douban.com/j/search_subjects";
    // 这条接口认 Referer：只带 UA 会回 400，补上 Referer 才是 200 和真 JSON。
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) "
            + "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0 Safari/537.36";

    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * 拉一页榜单。任何一步不对都抛 HotSourceException，由刷新侧决定「这次不写新批次」，
     * 所以读侧要么拿到带时间戳的真数据，要么明确知道外部源没通，不会拿到半截结果。
     */
    public List<HotItem> fetch(String tag, int limit) {
        if (limit < 1 || limit > 100) {
            throw new HotSourceException("榜单条数得在 1~100 之间，传的是 " + limit);
        }
        String url = ENDPOINT + "?type=movie&tag=" + encode(tag)
                + "&page_limit=" + limit + "&page_start=0";
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(8000);
            conn.setRequestProperty("User-Agent", USER_AGENT);
            conn.setRequestProperty("Referer", "https://movie.douban.com/");
            conn.setRequestProperty("Accept", "application/json");

            int status = conn.getResponseCode();
            String body = read(status >= 400 ? conn.getErrorStream() : conn.getInputStream());
            if (status != 200) {
                throw new HotSourceException("榜单源返回 " + status + "：" + brief(body));
            }

            JsonNode subjects = mapper.readTree(body).path("subjects");
            if (!subjects.isArray()) {
                throw new HotSourceException("榜单返回的不是 subjects 数组：" + brief(body));
            }
            List<HotItem> items = new ArrayList<>();
            for (JsonNode node : subjects) {
                String title = node.path("title").asText("").trim();
                if (title.isEmpty()) {
                    continue;
                }
                HotItem item = new HotItem();
                item.setTitle(title);
                item.setRate(node.path("rate").asText("").trim());
                item.setCover(node.path("cover").asText("").trim());
                item.setSubjectId(node.path("id").asText("").trim());
                item.setUrl(node.path("url").asText("").trim());
                items.add(item);
            }
            if (items.isEmpty()) {
                throw new HotSourceException("榜单是空的（接口没报错但一条都没解析出来）");
            }
            return items;
        } catch (HotSourceException e) {
            throw e;
        } catch (Exception e) {
            throw new HotSourceException("取榜单失败：" + e.getClass().getSimpleName() + " "
                    + (e.getMessage() == null ? "" : e.getMessage()), e);
        }
    }

    private static String encode(String tag) {
        try {
            return URLEncoder.encode(tag == null || tag.isEmpty() ? "热门" : tag,
                    StandardCharsets.UTF_8.name());
        } catch (Exception e) {
            throw new HotSourceException("榜单标签编码不了：" + tag, e);
        }
    }

    private static String read(InputStream stream) throws Exception {
        if (stream == null) {
            return "";
        }
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] chunk = new byte[4096];
        int read;
        while ((read = stream.read(chunk)) > 0) {
            buffer.write(chunk, 0, read);
        }
        return new String(buffer.toByteArray(), StandardCharsets.UTF_8);
    }

    private static String brief(String text) {
        if (text == null) {
            return "";
        }
        String flat = text.replaceAll("\\s+", " ").trim();
        return flat.length() > 200 ? flat.substring(0, 200) + "…" : flat;
    }

    /** 榜单里的一条，字段和豆瓣返回的一一对应，没做补全 */
    public static class HotItem {
        private String title;
        private String rate;
        private String cover;
        private String subjectId;
        private String url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getSubjectId() {
            return subjectId;
        }

        public void setSubjectId(String subjectId) {
            this.subjectId = subjectId;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class HotSourceException extends RuntimeException {
        public HotSourceException(String message) {
            super(message);
        }

        public HotSourceException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}

-- 外部热度快照表（2026-10-10）
--
-- 为什么要有这张表：库里 movie 的 release_time 最晚停在 2024-02-08，想看数也停在那时候，
-- 所以「最近什么在映、谁最热」这类问题拿站内数据答，答出来的是一份两年前的榜单，
-- 数字看着真、其实是旧的 —— 这比答「查不到」更坏。这张表存的是「某一刻从外部源看到的世界」，
-- 和 movie（站内可售影片）分开：外部源挂了不影响下单，站内数据也不会被外部源改写。
--
-- 智能体的 hot_now 工具只读这张表，不实时穿透外网；抓取由后台刷新触发，失败就不写新批次，
-- 于是读侧永远要么拿到带时间戳的真数据、要么明确知道没有。

CREATE TABLE IF NOT EXISTS movie_hot_snapshot (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    -- 一次抓取一个批次号（yyyyMMdd-HHmmss-SSS，到毫秒：同一秒里连点两次刷新不能撞成一批）。
    -- 留批次而不是原地覆盖，是为了能回答
    -- 「这份榜单是什么时候抓的」，也让刷新失败时旧批次原样还在。
    batch_id    VARCHAR(40)  NOT NULL,
    source      VARCHAR(20)  NOT NULL DEFAULT 'douban',
    -- 榜单里的名次，1 最热。MySQL 8 里 rank 是保留字（窗口函数），所以叫 rank_no。
    rank_no     INT          NOT NULL,
    title       VARCHAR(255) NOT NULL,
    -- 豆瓣评分原样存字符串：对方给的是 "6.2"，也可能给空串，不做数值化免得把「暂无」存成 0。
    rate        VARCHAR(10)  NOT NULL DEFAULT '',
    cover       VARCHAR(512) NOT NULL DEFAULT '',
    subject_id  VARCHAR(20)  NOT NULL DEFAULT '',
    subject_url VARCHAR(255) NOT NULL DEFAULT '',
    fetched_at  DATETIME     NOT NULL,
    UNIQUE KEY uk_batch_rank (batch_id, rank_no),
    KEY idx_fetched (fetched_at)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

-- 这张表由后端刷新逻辑写入（HotMovieSyncService），没有菜单和权限项：
-- 它不是一个可编辑的业务表，是一份带时间戳的外部观测记录。

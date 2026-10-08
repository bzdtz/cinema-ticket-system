-- 孤儿排片归位（2026-10-07）
-- 背景：场次 22/24/25/27 的 cinema_id 指向库里不存在的影院（10422/10423/10424），
-- 27 连 hall_id 1784872977 都没有行。后果是 app 侧 /showtimes/getById 是 INNER JOIN
-- cinema/hall，这四条在前端点不进去；智能体那侧走主键降级能看见，两边说法不一致。
-- 归属证据见 启动说明/审计-2026-10-06.md 第九节。
--
-- 只动 showtimes 的 4 行，不动 hall、不动 cinema、不动 seat，也不删任何行。
-- `` `order` `` 表在 25 上挂着 2 笔、27 上 2 笔，这四条不能删。
-- 影厅 1784872973「nn」/1784872974「向学楼」改完之后不再被任何场次引用，
-- 它们的 cinema_id 还是指向不存在的影院，这里不处理（把它们挂到真实影院下
-- 会让新建场次的影厅下拉里多出两个测试名，更糟）。

-- 先看清要动哪 4 行，以及动完之后落到哪家影院哪个影厅
SELECT s.id, m.name AS 影片, s.cinema_id AS 现影院, s.hall_id AS 现影厅,
       h.hall_name AS 现影厅名, s.showdate, s.showtime, s.sale,
       (SELECT COUNT(*) FROM `order` o WHERE o.showtimes_id = s.id) AS 牵连订单
FROM showtimes s
LEFT JOIN movie m ON m.id = s.movie_id
LEFT JOIN hall  h ON h.id = s.hall_id
WHERE s.id IN (22, 24, 25, 27)
ORDER BY s.id;

-- 目标影院/影厅都是库里已有的真实行：
--   1 万达影城（新乡万达IMAX店）：1 一号厅、2 IMAX厅、3 二号厅
--   2 万达影城（辉县）        ：19 一号厅、20 IMAX厅、18 二号厅
-- 27《大雨》放新乡万达二号厅，不放安阳万达——安阳万达唯一那个影厅叫「20240106TEST」。
START TRANSACTION;

UPDATE showtimes SET cinema_id = 1, hall_id = 1 WHERE id = 22;  -- 怒潮 → 新乡万达 一号厅
UPDATE showtimes SET cinema_id = 1, hall_id = 2 WHERE id = 24;  -- 怒潮 → 新乡万达 IMAX厅
UPDATE showtimes SET cinema_id = 2, hall_id = 19 WHERE id = 25; -- 怒潮 → 辉县万达 一号厅
UPDATE showtimes SET cinema_id = 1, hall_id = 3 WHERE id = 27;  -- 大雨 → 新乡万达 二号厅

-- 核对：期望 4 行全部 cinema_ok=1、hall_ok=1、hall_cinema 与 cinema_id 相等
SELECT s.id, m.name AS 影片, s.cinema_id, c.name AS 影院, s.hall_id,
       h.hall_name AS 影厅, h.cinema_id AS 影厅所属影院,
       (c.id IS NOT NULL) AS cinema_ok, (h.id IS NOT NULL) AS hall_ok,
       (h.cinema_id = s.cinema_id) AS 归属一致, s.showdate, s.sale
FROM showtimes s
LEFT JOIN movie m  ON m.id  = s.movie_id
LEFT JOIN cinema c ON c.id  = s.cinema_id
LEFT JOIN hall  h  ON h.id  = s.hall_id
WHERE s.id IN (22, 24, 25, 27)
ORDER BY s.id;

-- 全站再扫一遍：期望 0 行（再也没有指向缺失影院/影厅、或影厅不属于该影院的场次）
SELECT s.id, s.cinema_id, s.hall_id
FROM showtimes s
LEFT JOIN cinema c ON c.id = s.cinema_id
LEFT JOIN hall  h  ON h.id = s.hall_id
WHERE c.id IS NULL OR h.id IS NULL OR h.cinema_id <> s.cinema_id;

COMMIT;
-- 上面三条核对有任何一条不符合期望，把 COMMIT 换成 ROLLBACK 再执行。

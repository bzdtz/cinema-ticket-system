-- 把「这周六/这周日」的排片镜像到下一个周末（2026-10-10）
--
-- promote() 第一版按 7 天轮转排片，从周六往后数七天正好数不到「下周六」，
-- 而「下周六有什么场」是这条链路上最常见的问法之一。代码已经改成十天，
-- 这个脚本补的是那一次已经排下去的 20 条：把 10-10、10-11 两场的安排照抄到 10-17、10-18。
--
-- 座位矩阵用 INSERT ... SELECT 从同影院同厅的那一场复制，不在脚本里出现矩阵内容，
-- 也不新造布局：同一个厅的矩阵本来就是一份。

START TRANSACTION;

INSERT INTO showtimes (movie_id, cinema_id, hall_id, showtime, showdate, sale, seat)
SELECT movie_id, cinema_id, hall_id, showtime, '2026-10-17', sale, seat
FROM showtimes WHERE showdate = '2026-10-10';

INSERT INTO showtimes (movie_id, cinema_id, hall_id, showtime, showdate, sale, seat)
SELECT movie_id, cinema_id, hall_id, showtime, '2026-10-18', sale, seat
FROM showtimes WHERE showdate = '2026-10-11';

COMMIT;

-- 核一眼：这两天应该各有两场，影院/厅/时间和被镜像的那两天一致
SELECT showdate, COUNT(*) AS c FROM showtimes
WHERE showdate BETWEEN '2026-10-10' AND '2026-10-18' GROUP BY showdate ORDER BY showdate;

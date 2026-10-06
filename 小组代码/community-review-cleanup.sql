-- 社区功能（影评/想看）端到端测试的收尾清理
-- 已于 2026-10-05 执行完毕，保留此文件作为记录，方便下次压测/联调后复用。

USE theater;

-- 1) 恢复被测试覆盖的影片评分（原始值取自 all.sql 导入数据）
UPDATE movie SET score = 9.3 WHERE id = 1;
UPDATE movie SET score = 9.1 WHERE id = 45;

-- 2) 删除临时测试账号及其产生的数据
--    999001 = e2e_probe，999002 = e2e_intruder（用于验证越权删除返回 510）
DELETE FROM movie_comment WHERE user_id IN (999001, 999002);
DELETE FROM user_see_record WHERE user_id IN (999001, 999002);
DELETE FROM user WHERE id IN (999001, 999002);

-- 3) 自检：三条都应为 0 / 原值
SELECT id, score, want_number FROM movie WHERE id IN (1, 2, 45);
SELECT COUNT(*) AS comments FROM movie_comment;
SELECT COUNT(*) AS sees FROM user_see_record;

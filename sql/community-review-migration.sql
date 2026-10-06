-- 影评 / 想看 功能所需的表结构迁移
-- 执行：mysql -uroot -p1234 theater < community-review-migration.sql
-- 两张表当前均为 0 行，影响面为零。

-- 1) movie_comment 的 id 不是 auto_increment，而 PO 标了 @TableId(type = IdType.AUTO)，
--    直接 insert 会报 Column 'id' cannot be null。
ALTER TABLE movie_comment MODIFY id int NOT NULL AUTO_INCREMENT;

-- 2) movie_comment 原本没有任何存放影评正文的列（只有 likes / comment_id / score），
--    没有这一列「影评」功能无法落地。
ALTER TABLE movie_comment ADD COLUMN content varchar(1000) NULL AFTER movie_id;

-- 3) 按影片查影评列表、按作者查我的影评，都需要索引。
ALTER TABLE movie_comment ADD INDEX idx_mc_movie (movie_id);
ALTER TABLE movie_comment ADD INDEX idx_mc_user (user_id);

-- 4) user_see_record 同样的主键自增问题。
ALTER TABLE user_see_record MODIFY id int NOT NULL AUTO_INCREMENT;

-- 5) 同一用户对同一影片只能「想看」一次，防止重复计数把 want_number 刷爆。
ALTER TABLE user_see_record ADD UNIQUE KEY uk_usr_movie (user_id, movie_id);

-- 复核
SELECT TABLE_NAME, COLUMN_NAME, EXTRA
FROM information_schema.COLUMNS
WHERE TABLE_SCHEMA = 'theater'
  AND TABLE_NAME IN ('movie_comment', 'user_see_record')
  AND COLUMN_NAME = 'id';

-- 追加：score 原本是 double(10,0)，半星评分(如 7.5)会被 MySQL 四舍五入成整数，放宽到 1 位小数
ALTER TABLE movie_comment MODIFY score double(10,1) NULL;

-- ============================================================
-- 码上影院 权限/菜单数据对齐（审计-2026-10-06 第 3 项）
-- 只改数据，不动代码。可重复执行（幂等）。
-- 回滚：先跑本文件顶部的备份命令，再 source 那个 sql。
-- ============================================================

START TRANSACTION;

-- ---------- 1) 编辑电影必被 403：permission 表压根没有 /movie-cinema-mapping ----------
-- MovieTable.vue 的 saveEdit 每次点"确定"都会 POST /movie-cinema-mapping/update/{id}，
-- 拦截器查不到这条权限 → code 510 → 前端跳 403。两个角色都中招。
INSERT INTO permission (name, url, description, sn)
SELECT '电影影院映射', '/movie-cinema-mapping', '编辑电影时同步影院关联', NULL
WHERE NOT EXISTS (SELECT 1 FROM permission WHERE url = '/movie-cinema-mapping');

SET @mcm = (SELECT id FROM permission WHERE url = '/movie-cinema-mapping' LIMIT 1);

INSERT INTO role_permission (role_id, permission_id)
SELECT 1001, @mcm
WHERE NOT EXISTS (SELECT 1 FROM role_permission WHERE role_id = 1001 AND permission_id = @mcm);

INSERT INTO role_permission (role_id, permission_id)
SELECT 1002, @mcm
WHERE NOT EXISTS (SELECT 1 FROM role_permission WHERE role_id = 1002 AND permission_id = @mcm);

-- ---------- 2) 1001 看得到「排片表」却点进去 403：菜单有、权限缺 ----------
SET @st = (SELECT id FROM permission WHERE url = '/showtimes' LIMIT 1);

INSERT INTO role_permission (role_id, permission_id)
SELECT 1001, @st
WHERE @st IS NOT NULL
  AND NOT EXISTS (SELECT 1 FROM role_permission WHERE role_id = 1001 AND permission_id = @st);

-- ---------- 3) 超管(1002)侧边栏少了 7 项，只能手敲 URL ----------
-- 10 用户列表 / 11 影院列表 / 14 电影分类 / 20 菜单管理 / 21 角色管理 / 22 权限管理 / 28 管理员列表
INSERT INTO role_menu (role_id, menu_id)
SELECT 1002, m.id
FROM menu m
WHERE m.id IN (10, 11, 14, 20, 21, 22, 28)
  AND NOT EXISTS (SELECT 1 FROM role_menu rm WHERE rm.role_id = 1002 AND rm.menu_id = m.id);

-- ---------- 4) 孤儿映射：permission_id 指向不存在的权限行 ----------
-- 实测是 1001 的 permission_id IN (2,3) 两行（那是 menu.id，不是 permission.id）。
DELETE FROM role_permission
WHERE permission_id IS NULL
   OR permission_id NOT IN (SELECT id FROM permission);

-- ---------- 5) 重复行：同一 (role, permission) / (role, menu) 只留一条 ----------
DELETE rp FROM role_permission rp
JOIN role_permission rp2
  ON rp.role_id = rp2.role_id
 AND rp.permission_id = rp2.permission_id
 AND rp.id > rp2.id;

DELETE rm FROM role_menu rm
JOIN role_menu rm2
  ON rm.role_id = rm2.role_id
 AND rm.menu_id = rm2.menu_id
 AND rm.id > rm2.id;

COMMIT;

-- ============================================================
-- 校验
-- ============================================================
SELECT '== 每个角色的权限 url ==' AS '';
SELECT rp.role_id, GROUP_CONCAT(p.url ORDER BY p.url) AS urls
FROM role_permission rp JOIN permission p ON p.id = rp.permission_id
GROUP BY rp.role_id;

SELECT '== 每个角色的菜单 ==' AS '';
SELECT rm.role_id, GROUP_CONCAT(rm.menu_id ORDER BY rm.menu_id) AS menu_ids, COUNT(*) AS n
FROM role_menu rm GROUP BY rm.role_id;

SELECT '== 重复检测（应 0 行） ==' AS '';
SELECT role_id, permission_id, COUNT(*) c FROM role_permission GROUP BY role_id, permission_id HAVING c > 1
UNION ALL
SELECT role_id, menu_id, COUNT(*) c FROM role_menu GROUP BY role_id, menu_id HAVING c > 1;

SELECT '== 孤儿检测（应 0 行） ==' AS '';
SELECT 'role_permission' src, rp.id, rp.role_id, rp.permission_id
FROM role_permission rp LEFT JOIN permission p ON p.id = rp.permission_id WHERE p.id IS NULL
UNION ALL
SELECT 'role_menu', rm.id, rm.role_id, rm.menu_id
FROM role_menu rm LEFT JOIN menu m ON m.id = rm.menu_id WHERE m.id IS NULL;

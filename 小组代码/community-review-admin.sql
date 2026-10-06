-- 后台「影评管理」：开菜单 + 建权限 + 授权给两个角色
-- 依赖：manager 侧新增的 /movie-comment 接口（page / del / batch）

START TRANSACTION;

-- 1) 权限：/movie-comment 前缀可覆盖 /movie-comment/page、/movie-comment/del/1 等
INSERT INTO permission (id, name, url, description, sn)
VALUES (2128764936, '影评管理', '/movie-comment', '后台影评审核与删除', '1');

INSERT INTO role_permission (role_id, permission_id) VALUES (1001, 2128764936);
INSERT INTO role_permission (role_id, permission_id) VALUES (1002, 2128764936);

-- 2) 菜单：启用「评论管理」下原本 status=0 且没有路由的 25 号占位行
UPDATE menu
SET name = '影评管理',
    route = 'moviecomment',
    icon = 'el-icon-lx-cascades',
    status = '1'
WHERE id = 25;

INSERT INTO role_menu (role_id, menu_id) VALUES (1001, 25);
INSERT INTO role_menu (role_id, menu_id) VALUES (1002, 25);

COMMIT;

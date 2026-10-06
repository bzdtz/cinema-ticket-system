-- 权限匹配收紧前后的对照校验
--
-- PermissionMapper.xml 的 Check 原先是子串匹配：  path like '%permission.url%'
-- 现已改为按路径段的前缀匹配：                    path = permission.url or path like 'permission.url/%'
--
-- 子串匹配会让一个权限顺带放开名字里包含它的其它接口，实测存在两条真实串味：
--   /user         ->  /cinema-user/**   （只有"注册用户管理"的角色可以增删管理员账号）
--   /movie        ->  /movie-img/**
--
-- 用法：改掉 @acct 的值为要核查的管理员 account，整文件执行。
-- 只要「修复后可访问」没有变成 0，就说明这个接口没被收紧误伤。

SET @acct = 100002;

SELECT p.path                                                            AS 接口路径,
       o.cnt                                                             AS 修复前可访问,
       n.cnt                                                             AS 修复后可访问,
       IF(n.cnt = 0 AND o.cnt > 0, '!! 被误伤', 'ok')                     AS 结论
FROM (
  SELECT '/menu/tree'                            AS path UNION ALL
  SELECT '/menu/roleMenu/1001'                   UNION ALL
  SELECT '/cinema/getByPage'                     UNION ALL
  SELECT '/cinema/del/1'                         UNION ALL
  SELECT '/cinema/delBatch'                      UNION ALL
  SELECT '/cinema/getByAccount/x'                UNION ALL
  SELECT '/cinema-user/page'                     UNION ALL
  SELECT '/cinema-user/saveOrUpdate'             UNION ALL
  SELECT '/movie/page'                           UNION ALL
  SELECT '/movie/updateImg/1'                    UNION ALL
  SELECT '/movie-img/imgs/1'                     UNION ALL
  SELECT '/movie-type-mapping/add'               UNION ALL
  SELECT '/movietype/page'                       UNION ALL
  SELECT '/hall/page'                            UNION ALL
  SELECT '/hall/updateinit/1'                    UNION ALL
  SELECT '/showtimes/page'                       UNION ALL
  SELECT '/order/page'                           UNION ALL
  SELECT '/order/getOrderDetail/1'               UNION ALL
  SELECT '/order-detail/getSeat/1'               UNION ALL
  SELECT '/role-menu/updateMenuRole/1001'        UNION ALL
  SELECT '/role-permission/get/1001'             UNION ALL
  SELECT '/role-permission/changeRolePermissions/1001' UNION ALL
  SELECT '/permission/page'                      UNION ALL
  SELECT '/charact/page'                         UNION ALL
  SELECT '/user/page'                            UNION ALL
  SELECT '/ticket/getTic/1'
) p
JOIN LATERAL (
  SELECT COUNT(*) AS cnt
  FROM role_permission rp
  JOIN permission pm ON pm.id = rp.permission_id
  JOIN cinema_user cu ON cu.role = rp.role_id
  WHERE cu.account = @acct
    AND p.path LIKE CONCAT('%', pm.url, '%')
) o ON TRUE
JOIN LATERAL (
  SELECT COUNT(*) AS cnt
  FROM role_permission rp
  JOIN permission pm ON pm.id = rp.permission_id
  JOIN cinema_user cu ON cu.role = rp.role_id
  WHERE cu.account = @acct
    AND (p.path = pm.url OR p.path LIKE CONCAT(pm.url, '/%'))
) n ON TRUE;

-- 顺带看每个角色到底有哪些 url 权限
-- SELECT rp.role_id, GROUP_CONCAT(p.url ORDER BY p.url SEPARATOR '  ') AS urls, COUNT(*) n
-- FROM role_permission rp
-- JOIN permission p ON p.id = rp.permission_id
-- GROUP BY rp.role_id;

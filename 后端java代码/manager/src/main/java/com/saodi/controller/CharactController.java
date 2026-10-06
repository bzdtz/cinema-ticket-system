package com.saodi.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saodi.po.CinemaUser;
import com.saodi.po.Charact;
import com.saodi.po.RoleMenu;
import com.saodi.po.RolePermission;
import com.saodi.query.CharactQuery;
import com.saodi.service.ICharactService;
import com.saodi.service.ICinemaUserService;
import com.saodi.service.IRoleMenuService;
import com.saodi.service.IRolePermissionService;
import com.saodi.vo.ResponseObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author saodi
 * @since 2023-12-19
 */
@RestController
@RequestMapping("/charact")
public class CharactController {

    @Autowired
    ICharactService charactService;
    @Autowired
    ICinemaUserService cinemaUserService;
    @Autowired
    IRoleMenuService roleMenuService;
    @Autowired
    IRolePermissionService rolePermissionService;

    @PostMapping("/saveorupdate")
    public ResponseObj saveOrUpdate(@RequestBody Charact charact){
        if (charact.getId()==null){
            return ResponseObj.ERROR();
        }
        // 角色标识 sn 是 cinema_user.role / role_menu.role_id / role_permission.role_id 的外键语义，
        // 改 sn 等于把所有管理员的权限一起改掉，所以只允许改名。
        Charact exist = charactService.getById(charact.getId());
        if (exist == null) {
            return ResponseObj.ERROR(512, "角色不存在");
        }
        if (StringUtils.hasText(charact.getSn()) && !charact.getSn().equals(exist.getSn())) {
            return ResponseObj.ERROR(513, "角色标识(sn)不允许修改");
        }
        charact.setSn(exist.getSn());
        if (!StringUtils.hasText(charact.getName())) {
            return ResponseObj.ERROR(514, "角色名称不能为空");
        }
        boolean b = charactService.saveOrUpdate(charact);
        return b?ResponseObj.SUCCESS():ResponseObj.ERROR();
    }

    @PostMapping("/page")
    public ResponseObj page(@RequestBody CharactQuery charactQuery){
        Page<Charact> byPage = charactService.getByPage(charactQuery);
        return byPage!=null?ResponseObj.SUCCESS(byPage):ResponseObj.ERROR();
    }

    @PostMapping("/batchupdate")
    public ResponseObj batchUpdate(@RequestBody List<Charact> list){
        list.forEach(System.out::println);
        return null;
    }

    /**
     * 删除角色。
     * 之前后台根本没有这个接口，RoleTable.vue 的"删除/批量删除"只在前端 splice 本地数组，
     * 提示"删除成功"，刷新又全回来。
     */
    @GetMapping("/del/{id}")
    public ResponseObj del(@PathVariable("id") Integer id) {
        String err = deleteOne(id);
        return err == null ? ResponseObj.SUCCESS("删除成功") : ResponseObj.ERROR(515, err);
    }

    @PostMapping("/delBatch")
    public ResponseObj delBatch(@RequestBody List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return ResponseObj.ERROR(516, "没有选中要删除的角色");
        }
        List<String> failed = new ArrayList<>();
        for (Integer id : ids) {
            String err = deleteOne(id);
            if (err != null) {
                failed.add(err);
            }
        }
        if (failed.size() == ids.size()) {
            return ResponseObj.ERROR(515, String.join("；", failed));
        }
        return ResponseObj.SUCCESS(failed.isEmpty()
                ? "删除成功"
                : "部分删除成功，未删除：" + String.join("；", failed));
    }

    /** 返回 null 表示删除成功，否则返回不能删除的原因 */
    private String deleteOne(Integer id) {
        Charact charact = charactService.getById(id);
        if (charact == null) {
            return "角色不存在";
        }
        String sn = charact.getSn();
        Integer roleId = null;
        // role_menu.role_id / role_permission.role_id 是 int，sn 正常是纯数字字符串；
        // 脏数据（比如 "abc"）时不做隐式转换，免得 MySQL 报 truncated value。
        if (StringUtils.hasText(sn) && sn.matches("\\d+")) {
            roleId = Integer.valueOf(sn);
        }
        // 1) 还挂着管理员就不能删，否则这些账号登录后 role 指向不存在的角色 → 全站 403
        if (StringUtils.hasText(sn)) {
            QueryWrapper<CinemaUser> usedBy = new QueryWrapper<>();
            usedBy.eq("role", sn);
            long count = cinemaUserService.count(usedBy);
            if (count > 0) {
                return "角色「" + charact.getName() + "」下还有 " + count + " 个管理员账号，请先转移账号";
            }
        }
        // 2) 清掉该角色的菜单/权限映射，避免留下孤儿数据
        if (roleId != null) {
            QueryWrapper<RoleMenu> rm = new QueryWrapper<>();
            rm.eq("role_id", roleId);
            roleMenuService.remove(rm);
            QueryWrapper<RolePermission> rp = new QueryWrapper<>();
            rp.eq("role_id", roleId);
            rolePermissionService.remove(rp);
        }
        // 3) 删角色本身
        return charactService.removeById(id) ? null : "删除失败";
    }
}

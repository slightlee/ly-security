package com.ly.lyadmin.modules.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ly.common.utils.IDUtils;
import com.ly.common.utils.Result;
import com.ly.lyadmin.annotation.SysLog;
import com.ly.lyadmin.modules.sys.model.SysRole;
import com.ly.lyadmin.modules.sys.model.SysRoleMenu;
import com.ly.lyadmin.modules.sys.service.SysRoleMenuService;
import com.ly.lyadmin.modules.sys.service.SysRoleService;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Description: 角色信息
 * @Author: SLIGHTLEE
 * @Date: 2018/12/11 14:11
 */
@RestController
@RequestMapping("sys/role")
public class SysRoleController {

    @Autowired
    SysRoleService sysRoleService;

    @Autowired
    SysRoleMenuService sysRoleMenuService;


    /**
     * @Description: 角色列表
     * @Param:
     * @Return:
     * @Author: SLIGHTLEE
     * @Email: lmm_work@163.com
     * @Date: 2019/12/3 4:46 下午
     */
    @RequestMapping("/list")
    public Result list(@RequestParam Integer page, @RequestParam Integer limit,
                       @RequestParam(required = false) String searchKey,
                       @RequestParam(required = false) String searchValue){
        Result r = sysRoleService.getRoleList(page, limit, searchKey, searchValue);
        return r;
    }


    /**
     * @Description: 角色列表信息
     * @Param:
     * @Return:
     * @Author: SLIGHTLEE
     * @Email: lmm_work@163.com
     * @Date: 2019/12/13 4:11 下午
     */
    @RequestMapping("/rolelist")
    public Result rolelist(){
        List<SysRole> roleList = sysRoleService.list();
        return Result.ok().put("roleList",roleList);
    }

    /**
     * @Description: 根据用户编号查询角色编号
     * @Param:
     * @Return:
     * @Author: SLIGHTLEE
     * @Email: lmm_work@163.com
     * @Date: 2019/12/13 5:21 下午
     */
    @RequestMapping("/rolelistByUserId")
    public Result rolelistByUserId(Long userId){
        List<Long> roleIds = sysRoleService.rolelistByUserId(userId);
        return Result.ok().put("roleIds",roleIds);
    }



    /**
     * 角色信息
     */
    @RequestMapping("/roloInfo")
    public Result info(@RequestParam Long roleId){

        SysRole sysRole = new SysRole();
        //查询角色对应的菜单
        List<Long> menuIdList = sysRoleMenuService.queryMenuIdList(roleId);
        sysRole.setCodes(menuIdList);
        return Result.ok().put("sysRole", sysRole);
    }

    /**
     * @Description:  添加角色信息
     * @Param:
     * @Return:
     * @Author: SLIGHTLEE
     * @Email: lmm_work@163.com
     * @Date: 2019/12/9 10:10 上午
     */
    @RequestMapping("/save")
    public Result save(@RequestBody SysRole sysRole){

        // 添加角色
        long id = IDUtils.genItemId();
        sysRole.setRoleId(id);
        sysRole.setCreateTime(new Date());
        sysRoleService.save(sysRole);

       // 获取 codes  值
        List<Long> codes = sysRole.getCodes();

        // 添加 角色菜单
        SysRoleMenu sysRoleMenu = new SysRoleMenu();
        sysRoleMenu.setRoleId(id);
        for (int i = 0; i < codes.size(); i++) {
            sysRoleMenu.setMenuId(codes.get(i));
            sysRoleMenuService.save(sysRoleMenu);
            System.out.println(sysRoleMenu);
        }

        return Result.ok();
    }

    /**
     * @Description: 更新角色信息
     * @Param:
     * @Return:
     * @Author: SLIGHTLEE
     * @Email: lmm_work@163.com
     * @Date: 2019/12/12 4:14 下午
     */
    @RequestMapping("/update")
    public Result update(@RequestBody SysRole sysRole){

        // 修改角色信息
        sysRoleService.updateById(sysRole);

        //根据角色编号 删除 角色菜单
        QueryWrapper<SysRoleMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id",sysRole.getRoleId());
        sysRoleMenuService.remove(wrapper);

        // 获取 codes  值
        List<Long> codes = sysRole.getCodes();

        //添加角色 菜单
        SysRoleMenu sysRoleMenu = new SysRoleMenu();
        sysRoleMenu.setRoleId(sysRole.getRoleId());
        for (int i = 0; i < codes.size(); i++) {
            sysRoleMenu.setMenuId(codes.get(i));
            sysRoleMenuService.save(sysRoleMenu);
            System.out.println(sysRoleMenu);
        }

        return Result.ok();
    }


    /**
     * @Description: 删除角色
     * @Param:
     * @Return:
     * @Author: SLIGHTLEE
     * @Email: lmm_work@163.com
     * @Date: 2019/12/12 5:50 下午
     */
    @SysLog("删除角色")
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public Result delete(@RequestBody Long[] roleId){
        // 删除 角色菜单
        sysRoleMenuService.deleteBatch(roleId);
        // 删除 角色
        sysRoleService.removeByIds(Arrays.asList(roleId));
        return Result.ok();
    }
}

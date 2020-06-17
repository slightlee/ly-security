package com.ly.lyadmin.modules.sys.controller;

import com.ly.common.utils.Result;
import com.ly.lyadmin.annotation.SysLog;
import com.ly.lyadmin.modules.sys.model.SysMenu;
import com.ly.lyadmin.modules.sys.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description: 菜单信息
 *
 * @Author: SLIGHTLEE
 * @Date: 2019/11/4 10:17 下午
 *
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends AbstractController{

    @Autowired
    SysMenuService sysMenuService;

    /**
     *  导航菜单
     */
    @RequestMapping("/nav")
    public Result navigation(){
        List<SysMenu> menuList = sysMenuService.getUserMenuList(getUserId());
        return Result.ok().put("menuList",menuList);
    }


    @RequestMapping("/nav2")
    public Result navigation(Integer menuType){
        List<SysMenu> menuList = sysMenuService.getUserMenuList(getUserId(),menuType);
        return Result.ok().put("menuList",menuList);
    }


    /**
     *  所有菜单列表
     */
    @RequestMapping("/list")
    public Result list(){
        long beginTime = System.currentTimeMillis();
        List<SysMenu> menuList = sysMenuService.list(null);
        for (SysMenu sysMenu : menuList){
            SysMenu parentMenu = sysMenuService.getById(sysMenu.getParentId());
            if (parentMenu !=null){
                sysMenu.setParentName(parentMenu.getName());
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("花费时间" + (endTime - beginTime));
        return Result.ok().put("data",menuList);

    }


    @RequestMapping("queryListParentId")
    public Result queryListParentId(){
        List<SysMenu> menuList = sysMenuService.queryListParentId(0L);
        return  Result.ok().put("menuList",menuList);
    }

    /**
     * 添加菜单
     */
    @SysLog("添加菜单")
    @RequestMapping("/save")
    public Result save(@RequestBody SysMenu sysMenu){
        sysMenuService.save(sysMenu);
        return Result.ok();
    }

    /**
     * 删除菜单
     */
    @SysLog("删除菜单")
    @RequestMapping("/delete")
    public Result delete(Long menuId) {

        if (menuId <= 31) {
            return Result.error("系统菜单，不能删除");
        }
        //判断是否有子菜单或按钮
        List<SysMenu> menuList = sysMenuService.queryListParentId(menuId);
        if (menuList.size() > 0) {
            return Result.error("请先删除子菜单或按钮");
        }
        sysMenuService.delete(menuId);

        return Result.ok();
    }

}

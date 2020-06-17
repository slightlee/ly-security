package com.ly.lyadmin.modules.sys.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ly.common.utils.Constant;
import com.ly.lyadmin.modules.sys.mapper.SysMenuMapper;
import com.ly.lyadmin.modules.sys.mapper.SysUserMapper;
import com.ly.lyadmin.modules.sys.model.SysMenu;
import com.ly.lyadmin.modules.sys.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: TODO
 *
 * @Author: SLIGHTLEE
 * @Date: 2019/11/4 10:18 下午
 *
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    SysMenuMapper sysMenuMapper;

    @Autowired
    SysUserMapper sysUserMapper;

    @Override
    public List<SysMenu> queryListParentId(Long parentId, List<Long> menuIdList) {
        List<SysMenu> menuList = queryListParentId(parentId);
        if(menuIdList == null){
            return  menuList;
        }
        List<SysMenu>  userMenuList = new ArrayList<>();
        for (SysMenu sysMenu : menuList){
            if (menuIdList.contains(sysMenu.getMenuId())){
                userMenuList.add(sysMenu);
            }
        }
        return userMenuList;
    }


    @Override
    public List<SysMenu> queryListParentIdAndMenuType(Long parentId, Integer menuType, List<Long> menuIdList) {

        List<SysMenu> menuList = queryListParentIdAndMenuType(parentId,menuType);

        if(menuIdList == null){
            return  menuList;
        }
        List<SysMenu>  userMenuList = new ArrayList<>();
        for (SysMenu sysMenu : userMenuList){
            if (userMenuList.contains(sysMenu.getMenuId())){
                userMenuList.add(sysMenu);
            }
        }
        return userMenuList;
    }


    @Override
    public List<SysMenu> getUserMenuList(Long userId) {
        // 如果是系统管理员，拥有最高权限
        if(userId == Constant.SUPER_ADMIN){
            return getAllMenuList(null);
        }
        //用户菜单列表
        List<Long> menuIdList = sysUserMapper.queryAllMenuId(userId);
        return getAllMenuList(menuIdList);
    }



    @Override
    public List<SysMenu> getUserMenuList(Long userId,Integer menuType) {

        // 如果是系统管理员，拥有最高权限
        if(userId == Constant.SUPER_ADMIN){
//            return getAllMenuList(menuType,null);
            return getAllMenuList(null);
        }
        // 用户菜单列表

        return null;
    }


    /**
     * 获取所有菜单列表
     */
    public List<SysMenu> getAllMenuList(List<Long> menuIdList){
         // 查询根菜单 列表
          List<SysMenu> menuList = queryListParentId(0L, menuIdList);
        // 递归获取子菜单列表
        getMenuTreeList(menuList, menuIdList);
        return menuList;
    }


    public List<SysMenu> getAllMenuList(Integer menuType,List<Long> menuIdList){
        // 查询根菜单 列表
        List<SysMenu> menuList = queryListParentIdAndMenuType(0L,menuType,menuIdList);
        // 递归获取子菜单列表
        getMenuTreeList(menuList, menuIdList);
        return menuList;
    }

    /**
     * 递归
     */
    public List<SysMenu> getMenuTreeList(List<SysMenu> menuList,List<Long> menuIdList){

        List<SysMenu> subMenuList = new ArrayList<SysMenu>();
        for (SysMenu sysMenu : menuList){
          //目录
           if ( sysMenu.getType() == Constant.MenuType.CATALOG.getValue()){
                sysMenu.setList(getMenuTreeList(queryListParentId(sysMenu.getMenuId(),menuIdList),menuIdList));
            }
            subMenuList.add(sysMenu);
        }
        return subMenuList;
    }

    @Override
    public List<SysMenu> queryListParentId(Long parentId) {
        return sysMenuMapper.queryListParentId(parentId);
    }



    @Override
    public List<SysMenu> queryListParentIdAndMenuType(Long parentId, Integer menuType) {
        return sysMenuMapper.queryListParentIdAndMenuType(parentId,menuType);
    }



    @Override
    public void delete(Long menuId) {

        sysMenuMapper.deleteById(menuId);

        //删除菜单与角色关联

    }

}

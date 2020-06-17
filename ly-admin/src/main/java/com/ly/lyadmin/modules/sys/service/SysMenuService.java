package com.ly.lyadmin.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ly.lyadmin.modules.sys.model.SysMenu;

import java.util.List;

/**
 * @Description: TODO
 * 
 * @Author: SLIGHTLEE
 * @Date: 2019/11/4 10:59 下午
 * 
 */
public interface SysMenuService extends IService<SysMenu> {

   /**
    *  根据父菜单，查询子菜单     1
    */
    List<SysMenu> queryListParentId(Long parentId);

    /**
     *  根据父菜单，查询子菜单    1
     */
    List<SysMenu> queryListParentId(Long parentId, List<Long> menuIdList);


    /**
     *  根据父菜单以及菜单类型，查询子菜单   2
     */
    List<SysMenu> queryListParentIdAndMenuType(Long parentId, Integer menuType);


    /**
     *  根据父菜单以及菜单类型，查询子菜单   2
     */
    List<SysMenu> queryListParentIdAndMenuType(Long parentId, Integer menuType, List<Long> menuIdList);


    /**
     *  获取用户菜单列表
     */
    List<SysMenu> getUserMenuList(Long userId);

    List<SysMenu> getUserMenuList(Long userId, Integer menuType);

    /**
     * 删除
     */
    void delete(Long menuId);
}

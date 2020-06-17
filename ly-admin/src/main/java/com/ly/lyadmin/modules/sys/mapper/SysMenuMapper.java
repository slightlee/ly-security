package com.ly.lyadmin.modules.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ly.lyadmin.modules.sys.model.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     *  根据父菜单，查询子菜单
     */
    List<SysMenu> queryListParentId(Long parentId);


    /**
     *  根据父菜单以及菜单类型，查询子菜单
     */
    List<SysMenu> queryListParentIdAndMenuType(@Param(value = "parentId") Long parentId, @Param(value = "menuType") Integer menuType);

}
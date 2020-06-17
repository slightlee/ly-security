package com.ly.lyadmin.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ly.lyadmin.modules.sys.model.SysRoleMenu;

import java.util.List;

public interface SysRoleMenuService extends IService<SysRoleMenu> {

    List<Long> queryMenuIdList(Long roleId);

    void deleteBatch(Long[] roleIds);
}

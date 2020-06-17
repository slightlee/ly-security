package com.ly.lyadmin.modules.sys.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.ly.common.utils.Result;
import com.ly.lyadmin.modules.sys.model.SysRole;

import java.util.List;

public interface SysRoleService extends IService<SysRole> {

    /**
     *  根据条件 角色名称 模糊查询
     */
    Result getRoleList(Integer pageNo, Integer pageSize, String searchKey, String searchValue);

    /**
     *
     * 根据用户编号查询角色编号
     */
    List<Long> rolelistByUserId(Long userId);

}

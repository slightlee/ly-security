package com.ly.lyadmin.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ly.common.utils.Result;
import com.ly.lyadmin.modules.sys.model.SysUser;

import java.util.List;

/**
 * @Description: TODO
 * 
 * @Author: SLIGHTLEE
 * @Date: 2019/10/15 11:19 下午
 * 
 */
public interface SysUserService extends IService<SysUser> {

    /**
     *  查询用户的所有菜单ID
     * @param userId
     * @return
     */
    List<Long> queryAllMenuId(Long userId);

    /**
     *  根据条件 用户名 、手机号、邮箱 模糊查询
     */
    Result getAllUser(Integer pageNo, Integer pageSize, String searchKey, String searchValue);

    /**
     *  添加用户
     */
    void add(SysUser sysUser);

    /**
     * 修改用户
     *
     */
    void update(SysUser sysUser);

    void updateInfo(SysUser sysUser);

}

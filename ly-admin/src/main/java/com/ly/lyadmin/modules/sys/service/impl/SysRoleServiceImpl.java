package com.ly.lyadmin.modules.sys.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ly.common.utils.Result;
import com.ly.common.utils.StringUtil;
import com.ly.lyadmin.modules.sys.mapper.SysRoleMapper;
import com.ly.lyadmin.modules.sys.model.SysRole;
import com.ly.lyadmin.modules.sys.model.SysUser;
import com.ly.lyadmin.modules.sys.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: TODO
 * 
 * @Author: SLIGHTLEE
 * @Date: 2019/11/6 10:55 下午
 * 
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    SysRoleMapper sysRoleMapper;

    @Override
    public Result getRoleList(Integer pageNo, Integer pageSize, String searchKey, String searchValue) {

        QueryWrapper<SysRole> wrapper = new QueryWrapper<SysRole>();
        if(StringUtil.isNotBlank(searchKey)){
            wrapper.like(searchKey,searchValue);
        }
        IPage<SysRole> page = new Page<SysRole>(pageNo,pageSize);
        IPage<SysRole> iPage = sysRoleMapper.selectPage(page, wrapper);
        return Result.ok().put("count",iPage.getTotal()).put("data",iPage.getRecords());
    }

    @Override
    public List<Long> rolelistByUserId(Long userId) {
        return sysRoleMapper.rolelistByUserId(userId);
    }
}

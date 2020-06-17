package com.ly.lyadmin.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ly.common.utils.Result;
import com.ly.common.utils.StringUtil;
import com.ly.lyadmin.modules.sys.mapper.SysUserRoleMapper;
import com.ly.lyadmin.modules.sys.model.SysUserRole;
import com.ly.lyadmin.shiro.ShiroUtils;
import com.ly.lyadmin.modules.sys.mapper.SysUserMapper;
import com.ly.lyadmin.modules.sys.model.SysUser;
import com.ly.lyadmin.modules.sys.service.SysUserService;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Description: TODO
 * 
 * @Author: SLIGHTLEE
 * @Date: 2019/10/15 10:51 下午
 * 
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    SysUserRoleMapper sysUserRoleMapper;

    @Override
    public List<Long> queryAllMenuId(Long userId) {
        return sysUserMapper.queryAllMenuId(userId);
    }

    @Override
    public Result getAllUser(Integer pageNo, Integer pageSize, String searchKey, String searchValue) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<SysUser>();
        if(StringUtil.isNotBlank(searchKey)){
            wrapper.like(searchKey,searchValue);
        }
        IPage<SysUser> page = new Page<SysUser>(pageNo,pageSize);
        IPage<SysUser> iPage = sysUserMapper.selectPage(page, wrapper);
        return Result.ok().put("count",iPage.getTotal()).put("data",iPage.getRecords());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(SysUser sysUser) {
        sysUser.setCreateTime(new Date());
        //sha256加密
        String salt = RandomStringUtils.randomAlphanumeric(20);
        sysUser.setSalt(salt);
        sysUser.setPassword(ShiroUtils.sha256(sysUser.getPassword(),salt));
        sysUser.setCreateBy(ShiroUtils.getUserId());
        sysUserMapper.insert(sysUser);

        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();  // mybatisplus 配置 主键自增 AUTO 不需要 此查询
        wrapper.eq("username",sysUser.getUsername());
        SysUser user = sysUserMapper.selectOne(wrapper);

        // 添加用户角色
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setUserId(user.getUserId());
        sysUserRole.setRoleId(sysUser.getRoleId()); // 暂时一个角色
        sysUserRoleMapper.insert(sysUserRole);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysUser sysUser) {

        if(StringUtils.isNotBlank(sysUser.getPassword())){
            //sha256加密
            String salt = RandomStringUtils.randomAlphanumeric(20);
            sysUser.setSalt(salt);
            sysUser.setPassword(ShiroUtils.sha256(sysUser.getPassword(),salt));

            // 删除用户角色
            QueryWrapper<SysUserRole> wrapper = new QueryWrapper<>();
            wrapper.in("user_id",sysUser.getUserId());
            sysUserRoleMapper.delete(wrapper);
            // 添加用户角色
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(sysUser.getUserId());
            sysUserRole.setRoleId(sysUser.getRoleId()); // 暂时一个角色
            sysUserRoleMapper.insert(sysUserRole);
        }
        sysUserMapper.updateById(sysUser);


    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateInfo(SysUser sysUser) {

        if(StringUtils.isNotBlank(sysUser.getPassword())){
            //sha256加密
            String salt = RandomStringUtils.randomAlphanumeric(20);
            sysUser.setSalt(salt);
            sysUser.setPassword(ShiroUtils.sha256(sysUser.getPassword(),salt));

        }
        sysUserMapper.updateById(sysUser);

    }
}

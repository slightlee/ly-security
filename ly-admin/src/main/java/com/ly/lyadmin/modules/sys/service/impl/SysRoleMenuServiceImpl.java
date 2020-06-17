package com.ly.lyadmin.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ly.lyadmin.modules.sys.mapper.SysRoleMenuMapper;
import com.ly.lyadmin.modules.sys.model.SysRoleMenu;
import com.ly.lyadmin.modules.sys.service.SysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: TODO
 * @Author SLIGHTLEE
 * @Date 2019/12/11 4:21 下午
 * @Version V1.0
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

    @Autowired
    SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public List<Long> queryMenuIdList(Long roleId) {
        return sysRoleMenuMapper.queryMenuIdList(roleId);
    }

    @Override
    public void deleteBatch(Long[] roleIds) {
        sysRoleMenuMapper.deleteBatch(roleIds);
    }
}

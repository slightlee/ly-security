package com.ly.lyadmin.modules.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ly.lyadmin.modules.sys.model.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Description: TODO
 *
 * @Author: SLIGHTLEE
 * @Date: 2019/10/15 10:48 下午
 *
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {


    /**
     * 查询用户的所有权限
     * @return
     */
    List<String> queryAllPerms(Long userId);

    /**
     * 查询用户的所有菜单ID
     * @param userId
     * @return
     */
    List<Long> queryAllMenuId(Long userId);
}

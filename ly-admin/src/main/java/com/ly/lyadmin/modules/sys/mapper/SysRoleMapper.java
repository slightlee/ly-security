package com.ly.lyadmin.modules.sys.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ly.lyadmin.modules.sys.model.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<Long> rolelistByUserId(Long userId);

}
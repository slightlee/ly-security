package com.ly.lyadmin.modules.sys.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ly.common.utils.Result;
import com.ly.common.utils.StringUtil;
import com.ly.lyadmin.modules.sys.mapper.SysLogMapper;
import com.ly.lyadmin.modules.sys.model.SysLogs;
import com.ly.lyadmin.modules.sys.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: TODO
 * @Email: lmm_work@163.com
 * @Author: SLIGHTLEE
 * @Date: 2019/11/6 10:55 下午
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLogs> implements SysLogService {


    @Autowired
    SysLogMapper sysLogMapper;

    @Override
    public Result getAllLogs(Integer pageNo, Integer pageSize, String searchKey, String searchValue) {
        QueryWrapper<SysLogs> wrapper = new QueryWrapper<>();
        if(StringUtil.isNotBlank(searchKey)){
            wrapper.like(searchKey,searchValue);
        }
        IPage<SysLogs> page = new Page<>(pageNo,pageSize);
        IPage<SysLogs> iPage = sysLogMapper.selectPage(page, wrapper);
        return Result.ok().put("count",iPage.getTotal()).put("data",iPage.getRecords());
    }
}

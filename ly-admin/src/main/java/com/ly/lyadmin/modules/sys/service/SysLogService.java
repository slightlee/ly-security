package com.ly.lyadmin.modules.sys.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.ly.common.utils.Result;
import com.ly.lyadmin.modules.sys.model.SysLogs;

public interface SysLogService extends IService<SysLogs> {

    /**
     *  根据条件 模糊查询
     */
    Result getAllLogs(Integer pageNo, Integer pageSize, String searchKey, String searchValue);

}

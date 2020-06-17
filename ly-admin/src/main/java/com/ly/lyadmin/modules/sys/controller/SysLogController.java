package com.ly.lyadmin.modules.sys.controller;

import com.ly.common.utils.Result;
import com.ly.lyadmin.annotation.SysLog;
import com.ly.lyadmin.modules.sys.model.SysLogs;
import com.ly.lyadmin.modules.sys.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sys/log")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;


    /**
     * 查询列表
     */
    @SysLog("查询日志")
    @RequestMapping(value = "/list1")
    public Result list(){
        List<SysLogs> logsList = sysLogService.list();
        return Result.ok().put("logsList",logsList);
    }

    @SysLog("根据编号查询日志")
    @RequestMapping(value = "/list2")
    public Result list(@RequestParam(value = "id") Long id){
        SysLogs sysLogs = sysLogService.getById(id);
        return Result.ok().put("sysLogs",sysLogs);
    }



    /**
     * 列表
     */
    @RequestMapping(value = "/list")
    public Result list(@RequestParam Integer page, @RequestParam Integer limit,
                  @RequestParam(required = false) String searchKey,
                  @RequestParam(required = false) String searchValue){
        Result r = sysLogService.getAllLogs(page, limit, searchKey, searchValue);
        return r;
    }

}

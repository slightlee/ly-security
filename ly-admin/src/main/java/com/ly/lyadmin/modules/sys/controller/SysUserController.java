package com.ly.lyadmin.modules.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ly.common.utils.Result;
import com.ly.lyadmin.annotation.SysLog;
import com.ly.lyadmin.modules.sys.model.SysUser;
import com.ly.lyadmin.modules.sys.model.SysUserRole;
import com.ly.lyadmin.modules.sys.service.SysUserRoleService;
import com.ly.lyadmin.modules.sys.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @Description: 用户信息
 *
 * @Author: SLIGHTLEE
 * @Date: 2019/10/15 10:47 下午
 *
 */
@Api(description = "用户信息管理")
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController{


    // 注入另一种方式
    @Autowired
    private final SysUserService sysUserService;

    @Autowired
    public SysUserController(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }


    @Autowired
    SysUserRoleService sysUserRoleService;

    /**
     *  用户信息
     * @return userList
     */
    @RequestMapping("/list")
    public Result list(@RequestParam Integer page, @RequestParam Integer limit,
                       @RequestParam(required = false) String searchKey,
                       @RequestParam(required = false) String searchValue){
        Result r = sysUserService.getAllUser(page, limit, searchKey, searchValue);
        return r;
    }

    /**
     *   添加用户
     */
    @SysLog("添加用户")
 //   @ApiOperation(value = "添加用户" , notes="添加用户")
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public Result save(@RequestBody SysUser sysUser){

        // 添加用户
        sysUserService.add(sysUser);


        return Result.ok();
    }


    /**
     *   修改用户
     */
    @SysLog("修改用户")
  //  @ApiOperation(value = "修改用户" , notes="修改用户")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Result update(@RequestBody SysUser sysUser){
        sysUserService.update(sysUser);
        return Result.ok();
    }

    @SysLog("修改用户信息")
    //  @ApiOperation(value = "修改用户" , notes="修改用户")
    @RequestMapping(value = "/updateInfo",method = RequestMethod.POST)
    public Result updateInfo(@RequestBody SysUser sysUser){
        sysUserService.updateInfo(sysUser);
        return Result.ok();
    }


    /**
     *   删除用户
     */
    @SysLog("删除用户")
   // @ApiOperation(value = "删除用户" , notes="删除用户")
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public Result delete(@RequestBody Long[] userIds){
        if(ArrayUtils.contains(userIds,1L)){
            return Result.error("系统管理员不能删除");
        }
        if(ArrayUtils.contains(userIds, getUserId())){
            return Result.error("当前用户不能删除");
        }
        sysUserService.removeByIds(Arrays.asList(userIds));
        // 删除用户角色
        QueryWrapper<SysUserRole> wrapper = new QueryWrapper<>();
        wrapper.in("user_id",userIds);
        sysUserRoleService.remove(wrapper);

        return Result.ok();
    }

    /**
     *  获取登录的用户信息
     */
    @RequestMapping("/info")
    public Result info(){
        return Result.ok().put("user",getUser());
    }

    /**
     *  用户信息 不分页
     * @return userList
     */
  //  @ApiOperation(value = "用户信息" , notes="用户信息")
    @RequestMapping(value = "/userlist",method = RequestMethod.GET)
    public Result list(){
        List<SysUser> list = sysUserService.list();
        return Result.ok().put("list",list);
    }

}

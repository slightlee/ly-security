package com.ly.lyadmin.modules.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description: 欢迎页信息
 *
 * @Author: SLIGHTLEE
 * @Date: 2019/10/15 10:46 下午
 *
 */
@Controller
public class PageController {

    /**
     *  欢迎页
     */
    @RequestMapping(value = {"/", "index.html"})
    public String index(){
        return "views/" + "index";
    }


    /**
     *  登录
     */
    @RequestMapping("login.html")
    public String login(){
        return "views/" + "login";
    }


    /**
     * 控制台 跳转页面
     */
    @RequestMapping("/{url}/{page}")
    public String index(@PathVariable String url, @PathVariable String page){
        return "views/"+ url  + "/" + page;
    }




    /**
     *  用户 角色 菜单 跳转页面
     */
    @RequestMapping("/sys/{url}/{page}")
    public String userPage(@PathVariable String url, @PathVariable String page){
        return "views/sys"+"/"+url+"/" + page;
    }

    /**
     *  内容信息等 跳转页面
     */
    @RequestMapping("/bus/{url}/{page}")
    public String busPage(@PathVariable String url, @PathVariable String page){
        return "views/bus"+"/"+url+"/" + page;
    }

}

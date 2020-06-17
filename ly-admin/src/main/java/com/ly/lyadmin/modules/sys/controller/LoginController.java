package com.ly.lyadmin.modules.sys.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.ly.common.utils.Result;
import com.ly.lyadmin.shiro.ShiroUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;


/**
 * @Description: 登陆
 *
 * @Author: SLIGHTLEE
 * @Date: 2019/10/25 12:28 上午
 *
 */
@Controller
@RequestMapping("/sys")
public class LoginController {


    @Autowired
    private Producer producer;

    /**
     * @Description: 验证码
     * @Param:
     * @Return:
     * @Author: SLIGHTLEE
     * @Email: lmm_work@163.com
     * @Date: 2020/1/13 3:42 下午
     */
    @RequestMapping("captcha.jpg")
    public void captcha(HttpServletResponse response)throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //生成文字验证码
        String text = producer.createText();
        //生成图片验证码
        BufferedImage image = producer.createImage(text);
        //保存到shiro session
        ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
    }


    /**
     *  登录
     * @param username
     * @param password
     * @param captcha
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Result login(String username, String password, String captcha){

        String kaptcha = ShiroUtils.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
        if(!captcha.equalsIgnoreCase(kaptcha)){
            return Result.error("验证码不正确");
        }

        try {
            Subject subject = ShiroUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username,password);
            subject.login(token);
        }catch (UnknownAccountException e) {
            return Result.error(e.getMessage());
        }catch (IncorrectCredentialsException e) {
            return Result.error("账号或密码不正确");
        }catch (LockedAccountException e) {
            return Result.error("账号已被锁定,请联系管理员");
        }catch (AuthenticationException e) {
            return Result.error("账户验证失败");
        }

        return Result.ok();
    }

    /**
     *  退出
     * @return
     */
    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public String logout(){
        ShiroUtils.logout();
        return "redirect:login.html";
    }
}

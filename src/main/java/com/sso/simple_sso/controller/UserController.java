package com.sso.simple_sso.controller;

import com.sso.simple_sso.pojo.User;
import com.sso.simple_sso.service.UserService;
import com.sso.simple_sso.utils.CookieUtil;
import com.sso.simple_sso.utils.JwtUtil;
import com.sso.simple_sso.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author: bax
 * @create: 2022-08-07 14:51
 * @Description:
 */
@Slf4j
@Controller
@RequestMapping("/Admin")
public class UserController {

    @Autowired
    UserService userService;

    //@Value("${TTL}")
    //private String TTL;
    //
    //@Value("${refreshTTL}")
    //private String refreshTTL;
    //@PostMapping("/tokenlogin")
    //public Map<String,Object> Login(HttpServletResponse response, HttpServletRequest request,
    //                                @RequestParam("username") String username,@RequestParam("password") String password){
    //
    //
    //    Map<String,Object> map = new HashMap<>();
    //
    //    User UserMsg = userService.Login(username,password);
    //    // 这里因为是经过JWT验证过的
    //    if(UserMsg != null) {
    //        try {
    //            String token = JwtUtil.geToken(UserMsg,Long.parseLong(TTL) * 60000);
    //            String refreshToken = JwtUtil.geToken(UserMsg,Long.parseLong(refreshTTL)* 60000);
    //            log.info("token is" + token);
    //            log.info("refresh is" + refreshToken);
    //
    //            //将信息存入header中
    //            if (!(StringUtils.isBlank(token))) {
    //                map.put("username", UserMsg);
    //                map.put("token", token);
    //                map.put("msg", "登录成功");
    //                map.put("code", 200);
    //                CookieUtil.setCookie(request,response,"token",token, 60 * 60 * 7 * 24  ,true);
    //                CookieUtil.setCookie(request,response,"refreshToken",refreshToken,60 * 60 * 7 * 24 ,true);
    //            }
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //        }
    //    }else {
    //        map.put("msg","失败");
    //        map.put("code",100);
    //    }
    //
    //    return map;
    //}

    @ResponseBody
    @PostMapping(value = "/login")
    public Result ajaxLogin(String password, String username) {

        System.err.println("username" + username + "password" + password);


        User loginMsg = null;
        if (!StringUtils.isBlank(username)) {

            if (!StringUtils.isBlank(password)) {
                try {
                Subject subject = SecurityUtils.getSubject();
                UsernamePasswordToken token = new UsernamePasswordToken(username,password);
                subject.login(token);
                } catch (UnknownAccountException e) {
                    return new Result(false,"未知的账户",100);
                } catch (IncorrectCredentialsException e) {
                    return new Result(false,"密码错误",100);
                }catch (Exception e){
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
            }
        }
        return new Result(true,"成功",200);

        //if (loginMsg == null){
        //    return new Result(false,"失败",100);
        //}else {
        //    return new Result(true,"成功",200);
        //}

    }

    @RequestMapping("/logout")
    public String logOut(HttpServletRequest request,HttpServletResponse response){

        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/page/login";
    }

    @PostMapping("/register")
    public String register(User user){
        userService.register(user);
        return "redirect:/page/login";
    }

}

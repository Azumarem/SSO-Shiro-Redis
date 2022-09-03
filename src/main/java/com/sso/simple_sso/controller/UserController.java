package com.sso.simple_sso.controller;

import com.sso.simple_sso.filter.LoginRequired;
import com.sso.simple_sso.pojo.User;
import com.sso.simple_sso.service.UserService;
import com.sso.simple_sso.utils.CookieUtil;
import com.sso.simple_sso.utils.JwtUtil;
import com.sso.simple_sso.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: bax
 * @create: 2022-08-07 14:51
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/Admin")
public class UserController {

    @Autowired
    UserService userService;

    @Value("${TTL}")
    private String TTL;

    @Value("${refreshTTL}")
    private String refreshTTL;
    @PostMapping("/tokenlogin")
    public Map<String,Object> Login(HttpServletResponse response, HttpServletRequest request,
                                    @RequestParam("username") String username,@RequestParam("password") String password){


        Map<String,Object> map = new HashMap<>();

        User UserMsg = userService.Login(username,password);
        // 这里因为是经过JWT验证过的
        if(UserMsg != null) {
            try {
                String token = JwtUtil.geToken(UserMsg,Long.parseLong(TTL) * 60000);
                String refreshToken = JwtUtil.geToken(UserMsg,Long.parseLong(refreshTTL)* 60000);
                log.info("token is" + token);
                log.info("refresh is" + refreshToken);

                //将信息存入header中
                if (!(StringUtils.isBlank(token))) {
                    map.put("username", UserMsg);
                    map.put("token", token);
                    map.put("msg", "登录成功");
                    map.put("code", 200);
                    CookieUtil.setCookie(request,response,"token",token, 60 * 60 * 7 * 24  ,true);
                    CookieUtil.setCookie(request,response,"refreshToken",refreshToken,60 * 60 * 7 * 24 ,true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            map.put("msg","失败");
            map.put("code",100);
        }

        return map;
    }

    @PostMapping(value = "/login")
    public Result ajaxLogin(String password, String username, Model model) {

        System.err.println("username" + username + "password" + password);


        User loginMsg = null;
        if (!StringUtils.isBlank(username)) {
            if (!StringUtils.isBlank(password)) {
                loginMsg = userService.Login(username, password);

            }
        }

        if (loginMsg == null){
            return new Result(false,"失败",100);
        }else {
            return new Result(true,"成功",200);
        }

    }

    @PostMapping("loginOut")
    @ResponseBody
    public Result logOut(HttpServletRequest request,HttpServletResponse response){

        CookieUtil.deleteCookie(request,response,"token");
        CookieUtil.deleteCookie(request,response,"refreshToken");
        return new Result(true, "退出成功",600);
    }

}

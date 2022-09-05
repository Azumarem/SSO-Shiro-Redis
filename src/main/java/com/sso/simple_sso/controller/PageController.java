package com.sso.simple_sso.controller;


import com.sso.simple_sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author: bax
 * @create: 2022-08-06 00:22
 * @Description:
 */
@Controller
@RequestMapping("/page")
public class PageController {

    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/main")
    public String main(){
        return "main";
    }


    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/register")
    public String register(){
        return "register";
    }
}

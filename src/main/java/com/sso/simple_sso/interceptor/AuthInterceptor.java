package com.sso.simple_sso.interceptor;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.sso.simple_sso.pojo.User;
import com.sso.simple_sso.service.UserService;
import com.sso.simple_sso.utils.JwtUtil;
import com.sso.simple_sso.utils.Result;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: bax
 * @create: 2022-08-06 23:01
 * @Description:
 */
@Slf4j
@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //过滤ajax
        if(null != request.getHeader("X-Requested-With") && "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"))){
            return true;
        }

        //登陆信息
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        User user = userService.findByUserName(username);
        System.out.println(user);
        request.setAttribute("user",user);

        return true;
    }

    private void returnJson(HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            Map<String, Object> result = new HashMap<>();
            result.put("code",400);
            result.put("message","用户令牌token无效");
            result.put("data", null);
            response.getWriter().print(result);
        } catch (IOException e){

        } finally {

        }
    }
}

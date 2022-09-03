package com.sso.simple_sso.filter;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.sso.simple_sso.utils.JwtUtil;
import com.sso.simple_sso.utils.Result;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
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


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        JwtUtil jwtUtil = new JwtUtil();

        log.info("Token Interceptor preHandle ");

        if(!(handler instanceof HandlerMethod)){
            return true;
        }

        String token = request.getHeader("token");

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        if(method.isAnnotationPresent(LoginRequired.class)){
            LoginRequired loginRequired = method.getAnnotation(LoginRequired.class);
            if(loginRequired.loginSuccess()){
                if( token == null ){
                    throw new RuntimeException("无token请重新登陆");
                }

                Claims claims = JwtUtil.tokenToOut(token);
                log.info("Interceptor claims :",claims);

                if (claims!=null){
                    log.info("claims 有效");
                }else {
                    log.info("claims 过期");
                    returnJson(response);
                    return false;
                }
                return true;
            }
        }
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

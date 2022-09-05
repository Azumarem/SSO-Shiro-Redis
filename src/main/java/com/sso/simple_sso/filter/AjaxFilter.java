package com.sso.simple_sso.filter;

import com.sso.simple_sso.pojo.User;
import com.sso.simple_sso.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.servlet.AdviceFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: bax
 * @create: 2022-09-04 01:47
 * @Description:
 */
public class AjaxFilter extends AuthorizationFilter {


    //protected boolean isAjaxRequest(HttpServletRequest request, ServletResponse response){
    //    System.out.println("ajax");
    //    HttpServletRequest req = (HttpServletRequest) request;
    //
    //    if (req.getHeader("X-Requested-With") != null && "XMLHttpRequest".equalsIgnoreCase(req.getHeader("X-Requested-With"))){
    //        Subject subject = SecurityUtils.getSubject();
    //        if (subject.getPrincipal() == null){
    //            HttpServletResponse resp = (HttpServletResponse) response;
    //
    //            // 设置响应类型和编码字符 不然中文乱码
    //            resp.setContentType("application/json;charset=utf-8");
    //            resp.setCharacterEncoding("UTF-8");
    //
    //            return false;
    //        }else{
    //            return true;
    //        }
    //    }else {
    //        return true;
    //    }
    //
    //}

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        return false;
    }
}

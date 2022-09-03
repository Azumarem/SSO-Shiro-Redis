package com.sso.simple_sso.filter;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;



/**
 * @author: bax
 * @create: 2022-08-06 22:43
 * @Description:
 */
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    AuthInterceptor authInterceptor;

    /**
     * @author AzumaRem
     * @param registry:
     * @return void
     * @description 配置拦截器
     * @date 2022/8/6 22:48
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(authInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/Admin/**")
                .excludePathPatterns("/page/login")
                .excludePathPatterns("/static/**");

    }
}

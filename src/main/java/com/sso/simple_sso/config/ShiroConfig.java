package com.sso.simple_sso.config;


import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.sso.simple_sso.shiro.cache.RedisCache;
import com.sso.simple_sso.shiro.realms.CustomerRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.HashMap;
import java.util.Map;

/**
 * 用来整合shiro框架相关的配置类
 */
@Configuration
public class ShiroConfig {

    @Value("${spring.redis.host}")
    String redisHost;

    @Value("${spring.redis.port}")
    String redisPort;

    @Bean
    public RedisManager redisManager(){
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(redisHost + ":" + redisPort);
        redisManager.setTimeout(180000);
        return redisManager;
    }

    @Bean
    public JavaUuidSessionIdGenerator sessionIdGenerator(){
        return new JavaUuidSessionIdGenerator();
    }

    @Bean
    public RedisSessionDAO sessionDAO(){
        RedisSessionDAO sessionDAO = new RedisSessionDAO();
        sessionDAO.setRedisManager(redisManager());
        sessionDAO.setSessionIdGenerator(sessionIdGenerator());
        return sessionDAO;
    }

    @Bean
    public Realm getRealm(){

        System.err.println("realm");
        CustomerRealm customerRealm = new CustomerRealm();

        //修改凭证校验匹配器
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //设置加密算法为md5
        credentialsMatcher.setHashAlgorithmName("MD5");
        //设置散列次数
        credentialsMatcher.setHashIterations(1024);
        customerRealm.setCredentialsMatcher(credentialsMatcher);
        return new CustomerRealm();
    }

    @Bean
    public SimpleCookie cookie(){
        SimpleCookie cookie = new SimpleCookie("SHAREJESSIONID");
        cookie.setHttpOnly(true);
        cookie.setPath("/"); //多个系统共享session
        return cookie;
    }

    @Bean
    public DefaultWebSessionManager sessionManager(){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setGlobalSessionTimeout(-1000L); //设置session超时
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setSessionDAO(sessionDAO());
        sessionManager.setSessionIdCookie(cookie());
        return sessionManager;
    }

    @Bean
    public RedisCacheManager redisCacheManager(){
        RedisCacheManager cacheManager = new RedisCacheManager();
        cacheManager.setRedisManager(redisManager());
        return cacheManager;
    }

    /**
     * @author AzumaRem
     * @param :
     * @return DefaultWebSecurityManager
     * @description 配置SecurityManager
     * @date 2022/9/4 21:56
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(Realm realm){
        System.out.println("securityManager!!!");
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        securityManager.setSessionManager(sessionManager());
        securityManager.setCacheManager(redisCacheManager()); // 配置缓存的话，退出登录的时候crazycake会报错，要求放在session里面的实体类必须有个id标识
        return securityManager;
    }


    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        return new DefaultAdvisorAutoProxyCreator();
    }

    @Bean
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }



    //1.创建shiroFilter  //负责拦截所有请求
    @Bean(value = "shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        //给filter设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);

        //配置系统受限资源
        //配置系统公共资源
        Map<String,String> map = new HashMap<String,String>();
        map.put("/login.html","anon");//anon 设置为公共资源  放行资源放在下面
        map.put("/Admin/**","anon");//anon 设置为公共资源  放行资源放在下面
        map.put("/page/login","anon");
        map.put("/page/register","anon");
        map.put("/static/**","anon");

        map.put("/**","authc");//authc 请求这个资源需要认证和授权

        //默认认证界面路径
        shiroFilterFactoryBean.setLoginUrl("/page/login");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);


        return shiroFilterFactoryBean;
    }

    //2.创建安全管理器
    //@Bean
    //public DefaultWebSecurityManager getDefaultWebSecurityManager(Realm realm){
    //    DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
    //    //给安全管理器设置
    //    defaultWebSecurityManager.setRealm(realm);
    //
    //    return defaultWebSecurityManager;
    //}

    //3.创建自定义realm
    //@Bean
    //public Realm getRealm(){
    //    CustomerRealm customerRealm = new CustomerRealm();
    //
    //    //修改凭证校验匹配器
    //    HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
    //    //设置加密算法为md5
    //    credentialsMatcher.setHashAlgorithmName("MD5");
    //    //设置散列次数
    //    credentialsMatcher.setHashIterations(1024);
    //    customerRealm.setCredentialsMatcher(credentialsMatcher);
    //
    //
    //    //开启缓存管理
    //    //customerRealm.setCacheManager(new RedisCacheManager());
    //    //customerRealm.setCachingEnabled(true);//开启全局缓存
    //    //customerRealm.setAuthenticationCachingEnabled(true);//认证认证缓存
    //    //customerRealm.setAuthenticationCacheName("authenticationCache");
    //    //customerRealm.setAuthorizationCachingEnabled(true);//开启授权缓存
    //    //customerRealm.setAuthorizationCacheName("authorizationCache");
    //
    //    return customerRealm;
    //}

}

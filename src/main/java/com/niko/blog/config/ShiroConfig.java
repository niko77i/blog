package com.niko.blog.config;

import cn.hutool.core.map.MapUtil;
import com.niko.blog.shiro.AccountRealm;
import com.niko.blog.shiro.AuthFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Configuration
public class ShiroConfig {

    @Bean
    public SecurityManager securityManager(AccountRealm accountRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        securityManager.setRealm(accountRealm);
        log.info("---------> securityManager注入成功");
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        filterFactoryBean.setSecurityManager(securityManager);
        //配置登录的url和登录成功的url
        filterFactoryBean.setLoginUrl("/login");
        filterFactoryBean.setSuccessUrl("/user/center");
        //配置未授权跳转页面
        filterFactoryBean.setUnauthorizedUrl("/error/403");

        //配置shiro过滤器
        filterFactoryBean.setFilters(MapUtil.of("auth",authFilter()));

        Map<String, String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("/res/**","anon");

        linkedHashMap.put("/user/home","auth");  //认证之后才能进入
        linkedHashMap.put("/user/set","auth");  //认证之后才能进入
        linkedHashMap.put("/user/upload","auth");  //认证之后才能进入
        linkedHashMap.put("/user/repass","auth");  //认证之后才能进入
        linkedHashMap.put("/user/index","auth");  //认证之后才能进入
        linkedHashMap.put("/user/public","auth");  //认证之后才能进入
        linkedHashMap.put("/user/publicTotalAndCollectionTotal","auth");  //认证之后才能进入
        linkedHashMap.put("/user/collection","auth");  //认证之后才能进入
        linkedHashMap.put("/user/message","auth");  //认证之后才能进入
        linkedHashMap.put("/message/remove","auth");  //认证之后才能进入
        linkedHashMap.put("/message/nums","auth");  //认证之后才能进入
        linkedHashMap.put("/message/read","auth");  //认证之后才能进入
        linkedHashMap.put("/message/readDown","auth");  //认证之后才能进入

        linkedHashMap.put("/collection/remove/","auth");  //认证之后才能进入
        linkedHashMap.put("/collection/find/","auth");  //认证之后才能进入
        linkedHashMap.put("/collection/add/","auth");  //认证之后才能进入

        linkedHashMap.put("/post/edit","auth");  //认证之后才能进入
        linkedHashMap.put("/post/submit","auth");  //认证之后才能进入
        linkedHashMap.put("/post/delete","auth");  //认证之后才能进入
        linkedHashMap.put("/post/reply","auth");  //认证之后才能进入

        linkedHashMap.put("/websocket", "anon");
        linkedHashMap.put("/login","anon");
        filterFactoryBean.setFilterChainDefinitionMap(linkedHashMap);

        return filterFactoryBean;
    }

    @Bean
    public AuthFilter authFilter(){
        return new AuthFilter();
    }

}

package com.kanojo.config.security.common;

import com.kanojo.config.security.bean.IgnoreUrlsConfig;
import com.kanojo.config.security.bean.MyJWT;
import com.kanojo.config.security.bean.RestfulAccessDeniedHandler;
import com.kanojo.config.security.bean.RestfulAuthenticationEntryPoint;
import com.kanojo.config.security.bean.JwtAuthenticationTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * SpringSecurity通用配置
 * 注入所需bean
 * 包括通用Bean、Security通用Bean及动态权限通用Bean
 */
@Configuration
public class CommonSecurityConfig {

    /**
     * 密码加密
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 白名单配置类
     */
    @Bean
    public IgnoreUrlsConfig ignoreUrlsConfig() {
        return new IgnoreUrlsConfig();
    }

    /**
     * 利用hutool工具写的jwt工具类
     */
    @Bean
    public MyJWT jwtTokenUtil() {
        return new MyJWT();
    }

    /**
     * 自定义没有权限访问返回结果
     */
    @Bean
    public RestfulAccessDeniedHandler restfulAccessDeniedHandler() {
        return new RestfulAccessDeniedHandler();
    }

    /**
     * 自定义登录过期和未登录
     */
    @Bean
    public RestfulAuthenticationEntryPoint restAuthenticationEntryPoint() {
        return new RestfulAuthenticationEntryPoint();
    }

    /**
     * 自定义JWT登录授权过滤器
     */
    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter();
    }

//    @Bean
//    public DynamicAccessDecisionManager dynamicAccessDecisionManager() {
//        return new DynamicAccessDecisionManager();
//    }
//
//    @Bean
//    public DynamicSecurityMetadataSource dynamicSecurityMetadataSource() {
//        return new DynamicSecurityMetadataSource();
//    }
//
//    @Bean
//    public DynamicSecurityFilter dynamicSecurityFilter(){
//        return new DynamicSecurityFilter();
//    }
}

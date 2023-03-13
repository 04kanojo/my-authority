package com.kanojo.config.security.common;

import com.kanojo.config.security.bean.IgnoreUrlsConfig;
import com.kanojo.config.security.bean.JwtAuthenticationTokenFilter;
import com.kanojo.config.security.bean.RestfulAccessDeniedHandler;
import com.kanojo.config.security.bean.RestfulAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * SpringSecurity相关配置
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * 白名单配置类
     */
    @Autowired
    private IgnoreUrlsConfig ignoreUrlsConfig;

    /**
     * 自定义没有权限访问类
     */
    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;

    /**
     * 自定义未登录或者登录过期类
     */
    @Autowired
    private RestfulAuthenticationEntryPoint restAuthenticationEntryPoint;

    /**
     * 自定义jwt拦截器
     */
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

//    @Autowired(required = false)
//    private DynamicSecurityService dynamicSecurityService;
//    @Autowired(required = false)
//    private DynamicSecurityFilter dynamicSecurityFilter;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = httpSecurity.authorizeRequests();
        //不需要保护的资源路径允许访问
        for (String url : ignoreUrlsConfig.getUrls()) {
            registry.antMatchers(url).permitAll();
        }
        //允许跨域请求的OPTIONS请求
        registry.antMatchers(HttpMethod.OPTIONS).permitAll();
        // 任何请求需要身份认证
        registry.and().authorizeRequests().anyRequest().authenticated()
                // 关闭跨站请求防护及不使用session
                .and().csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 自定义权限拒绝处理类
                .and().exceptionHandling().accessDeniedHandler(restfulAccessDeniedHandler).authenticationEntryPoint(restAuthenticationEntryPoint)
                // 自定义权限拦截器JWT过滤器
                .and().addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
//        //有动态权限配置时添加动态权限校验过滤器
//        if (dynamicSecurityService != null) {
//            registry.and().addFilterBefore(dynamicSecurityFilter, FilterSecurityInterceptor.class);
//        }
        return httpSecurity.build();
    }
}

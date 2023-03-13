package com.kanojo.config.security.bean;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 自定义JWT登录授权过滤器
 */
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private MyJWT myJWT;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @CreateCache(name = "username_userDetails", expire = 60, timeUnit = TimeUnit.MINUTES)
    private Cache<String, UserDetails> userCache;

    /**
     * 此过滤器将过滤全部请求,仅对jwt进行校验
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        //根据Authorization请求头获取token数据
        String authToken = request.getHeader(this.tokenHeader);
        if (authToken != null) {
            //通过token解析出用户名字
            String username = myJWT.parse(authToken, "username").toString();
            //如果取出来用户不为空 && redis里面有信息(没过期) &&上下文对象为空
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userCache.get(username);
                //如果用户不为空
                if (userDetails != null) {
                    //设置上下文对象（判定已登录）
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        chain.doFilter(request, response);
    }
}

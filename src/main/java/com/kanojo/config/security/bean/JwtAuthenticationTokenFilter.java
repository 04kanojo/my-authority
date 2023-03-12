package com.kanojo.config.security.bean;

import com.kanojo.exception.MyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 自定义JWT登录授权过滤器
 */
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
//    @Autowired
//    private UserDetailsService userDetailsService;
    @Autowired
    private MyJWT myJWT;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        //根据Authorization请求头获取token数据
        String authToken = request.getHeader(this.tokenHeader);
        //获取成功
        if (authToken != null) {
            //通过token获取用户名
            String username = myJWT.getValue("username", authToken).toString();
            log.info("checking username:{}", username);

            //token中解析出了username
            //并且security上下文对象为空
            if (!Objects.isNull(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
//                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                //token无效
                if (!myJWT.verify(authToken)) {
                    throw new MyException("登陆过期");
//                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                    //设置细节
//                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                    log.info("authenticated user:{}", username);
//                    //token有效,重新设置上下文对象
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        //放行
        chain.doFilter(request, response);
    }
}

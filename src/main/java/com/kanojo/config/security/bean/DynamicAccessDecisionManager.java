package com.kanojo.config.security.bean;

import com.kanojo.config.security.bean.IgnoreUrlsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * 动态权限决策管理器，用于判断用户是否有访问权限
 */
//@Component
public class DynamicAccessDecisionManager implements AccessDecisionManager {

    @Autowired
    private IgnoreUrlsConfig ignoreUrlsConfig;

    /**
     * @param authentication:   当前登录用户的角色信息
     * @param object:           请求url信息
     * @param configAttributes: `UrlFilterInvocationSecurityMetadataSource`中的getAttributes方法传来的，表示当前请求需要的角色（可能有多个）
     */
    @Override
    public void decide(Authentication authentication, Object object,
                       Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        // 获取url
        FilterInvocation filterInvocation = (FilterInvocation) object;
        String requestUrl = filterInvocation.getRequestUrl();
        //白名单请求直接放行
        for (String url : ignoreUrlsConfig.getUrls()) {
            if (requestUrl.equals(url)) {
                return;
            }
        }
        //遍历此方法需要的url集合
        for (ConfigAttribute configAttribute : configAttributes) {
            //接口所需要的资源与访问人所拥有的资源进行url比对
            String needAuthority = configAttribute.getAttribute();
            for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
                if (needAuthority.trim().equals(grantedAuthority.getAuthority())) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("权限不足,无法访问");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

}
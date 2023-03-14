package com.kanojo.config.security.bean;

import com.kanojo.common.exception.MyException;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * 动态权限决策管理器，用于判断用户是否有访问权限
 */
@Component
public class DynamicAccessDecisionManager implements AccessDecisionManager {

    /**
     * @param authentication:   当前登录用户的角色信息
     * @param object:           请求url信息
     * @param configAttributes: 角色id
     */
    @Override
    public void decide(Authentication authentication, Object object,
                       Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        //白名单直接放行 || 不需要权限访问
        if (configAttributes == null) {
            return;
        }

        //遍历动态权限数据源
        for (ConfigAttribute configAttribute : configAttributes) {
            //获取能访问此url的角色id
            String roleId = configAttribute.getAttribute();
            for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
                //获取登录用户已有的角色Id
                String loginUserRoleId = grantedAuthority.getAuthority();
                //如果匹配任意一个,放行
                if (roleId.equals(loginUserRoleId)) {
                    return;
                }
            }
        }
        throw new MyException("你小子权限不太够呀");
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
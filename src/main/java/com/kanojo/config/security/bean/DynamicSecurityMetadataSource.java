package com.kanojo.config.security.bean;

import com.kanojo.config.security.bean.IgnoreUrlsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 动态权限数据源，用于获取动态权限规则
 */
//@Component
public class DynamicSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private IgnoreUrlsConfig ignoreUrlsConfig;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        List<ConfigAttribute> configAttributes = new ArrayList<>();
//        //获取当前访问的路径
//        String url = ((HttpServletRequest) o).getRequestURL().toString();
//        String path = URLUtil.getPath(url);
//        PathMatcher pathMatcher = new AntPathMatcher();
//        //获取访问该路径所需资源
//        //url-ConfigAttribute
//        for (String pattern : configAttributeMap.keySet()) {
//            if (pathMatcher.match(pattern, path)) {
//                configAttributes.add(configAttributeMap.get(pattern));
//            }
//        }
//        // 未设置操作请求权限，返回空集合
        configAttributes.add((ConfigAttribute) () -> "fuck");
        return configAttributes;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}

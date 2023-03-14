package com.kanojo.config.security.bean;

import com.kanojo.modules.model.ResourceRelation;
import com.kanojo.modules.service.ResourceRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 动态权限数据源，用于获取动态权限规则
 */
@Component
public class DynamicSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private IgnoreUrlsConfig ignoreUrlsConfig;

    @Autowired
    private ResourceRelationService resourceRelationService;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        List<ConfigAttribute> configAttributes = new ArrayList<>();
        //获取当前访问的路径
        FilterInvocation filterInvocation = (FilterInvocation) o;
        String requestUrl = filterInvocation.getRequestUrl();
        for (String url : ignoreUrlsConfig.getUrls()) {
            if (requestUrl.equals(url)) {
                return null;
            }
        }
        //根据url获取 -> 能访问该url的角色id
        List<Long> resourceIds = resourceRelationService
                .getResourceRelationByUrl(requestUrl)
                .stream()
                .map(ResourceRelation::getRoleId)
                .collect(Collectors.toList());

        //此资源没有进入数据库(不需要权限访问)
        if (resourceIds.size() == 0) {
            return null;
        }
        //全部添加进入集合返回给决策管理器
        for (Long resourceId : resourceIds) {
            configAttributes.add(new SecurityConfig(resourceId.toString()));
        }
        return configAttributes;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}

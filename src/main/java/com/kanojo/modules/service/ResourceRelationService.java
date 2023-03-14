package com.kanojo.modules.service;

import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kanojo.modules.model.ResourceRelation;

import java.util.List;
import java.util.concurrent.TimeUnit;


public interface ResourceRelationService extends IService<ResourceRelation> {

    /**
     * 获取资源id集合(本质)
     */
    @Cached(name = "url_resources_", expire = 30, timeUnit = TimeUnit.MINUTES)
    List<ResourceRelation> getResourceRelationByUrl(String requestUrl);
}

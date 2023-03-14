package com.kanojo.modules.service;

import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kanojo.modules.model.Resource;

import java.util.concurrent.TimeUnit;


public interface ResourceService extends IService<Resource> {

    @Cached(name = "url_resource", expire = 30, timeUnit = TimeUnit.MINUTES)
    Resource getByUrl(String requestUrl);
}

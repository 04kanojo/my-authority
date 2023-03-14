package com.kanojo.modules.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kanojo.modules.mapper.ResourceMapper;
import com.kanojo.modules.model.Resource;
import com.kanojo.modules.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements ResourceService {
    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    public Resource getByUrl(String requestUrl) {
        LambdaQueryWrapper<Resource> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Resource::getUrl, requestUrl);
        return resourceMapper.selectOne(lqw);
    }
}





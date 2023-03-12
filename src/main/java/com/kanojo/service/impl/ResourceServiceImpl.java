package com.kanojo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kanojo.module.Resource;
import com.kanojo.service.ResourceService;
import com.kanojo.mapper.ResourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements ResourceService {
    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    public List<Resource> getResources(List<Long> resourceIds) {
        return resourceMapper.selectBatchIds(resourceIds);
    }
}





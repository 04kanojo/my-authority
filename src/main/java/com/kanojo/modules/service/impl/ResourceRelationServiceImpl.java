package com.kanojo.modules.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kanojo.modules.mapper.ResourceRelationMapper;
import com.kanojo.modules.model.ResourceRelation;
import com.kanojo.modules.service.ResourceRelationService;
import com.kanojo.modules.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ResourceRelationServiceImpl extends ServiceImpl<ResourceRelationMapper, ResourceRelation> implements ResourceRelationService {

    @Autowired
    private ResourceRelationMapper resourceRelationMapper;

    @Autowired
    private ResourceService resourceService;

    @Override
    public List<ResourceRelation> getResourceRelationByUrl(String requestUrl) {
        Long resourceId = resourceService.getByUrl(requestUrl).getId();
        LambdaQueryWrapper<ResourceRelation> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ResourceRelation::getResourceId, resourceId);
        return resourceRelationMapper.selectList(lqw);
    }
}





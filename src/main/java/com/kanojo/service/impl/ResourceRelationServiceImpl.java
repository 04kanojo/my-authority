package com.kanojo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kanojo.module.ResourceRelation;
import com.kanojo.service.ResourceRelationService;
import com.kanojo.mapper.ResourceRelationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class ResourceRelationServiceImpl extends ServiceImpl<ResourceRelationMapper, ResourceRelation> implements ResourceRelationService {

    @Autowired
    private ResourceRelationMapper resourceRelationMapper;

    @Override
    public Set<Long> getResourceIdList(List<Long> roleIdList) {
        Set<Long> set = new HashSet<>();
        for (Long roleId : roleIdList) {
            LambdaQueryWrapper<ResourceRelation> lqw = new LambdaQueryWrapper<>();
            lqw.eq(ResourceRelation::getRoleId, roleId);
            //获取资源id数组
            List<Long> resourceIds = resourceRelationMapper
                    .selectList(lqw)
                    .stream().
                    map(ResourceRelation::getResourceId)
                    .collect(Collectors.toList());
            set.addAll(resourceIds);
        }
        return set;
    }
}





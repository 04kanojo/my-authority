package com.kanojo.service;

import com.kanojo.module.ResourceRelation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;


public interface ResourceRelationService extends IService<ResourceRelation> {

    /**
     * 获取资源id集合
     */
    Set<Long> getResourceIdList(List<Long> roleIdList);
}

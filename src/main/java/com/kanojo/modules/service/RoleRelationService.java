package com.kanojo.modules.service;

import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kanojo.modules.model.RoleRelation;

import java.util.List;
import java.util.concurrent.TimeUnit;


public interface RoleRelationService extends IService<RoleRelation> {

    @Cached(name = "id_roleIds_", expire = 30, timeUnit = TimeUnit.MINUTES)
    List<RoleRelation> getRoleRelationByAdminId(Long id);
}

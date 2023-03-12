package com.kanojo.service;

import com.kanojo.module.RoleRelation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface RoleRelationService extends IService<RoleRelation> {

    /**
     * 根据管理员id获取角色id数组
     */
    List<Long> getRoleIdList(Long adminId);
}

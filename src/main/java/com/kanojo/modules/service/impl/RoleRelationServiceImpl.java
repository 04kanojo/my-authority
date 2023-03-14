package com.kanojo.modules.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kanojo.modules.mapper.RoleRelationMapper;
import com.kanojo.modules.model.RoleRelation;
import com.kanojo.modules.service.RoleRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RoleRelationServiceImpl extends ServiceImpl<RoleRelationMapper, RoleRelation> implements RoleRelationService {

    @Autowired
    private RoleRelationMapper roleRelationMapper;

    @Override
    public List<RoleRelation> getRoleRelationByAdminId(Long adminId) {
        LambdaQueryWrapper<RoleRelation> lqw = new LambdaQueryWrapper<>();
        lqw.eq(RoleRelation::getAdminId, adminId);
        return roleRelationMapper.selectList(lqw);
    }
}





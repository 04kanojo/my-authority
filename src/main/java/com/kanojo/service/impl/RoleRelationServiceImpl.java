package com.kanojo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kanojo.module.RoleRelation;
import com.kanojo.service.RoleRelationService;
import com.kanojo.mapper.RoleRelationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class RoleRelationServiceImpl extends ServiceImpl<RoleRelationMapper, RoleRelation> implements RoleRelationService {

    @Autowired
    private RoleRelationMapper roleRelationMapper;

    @Override
    public List<Long> getRoleIdList(Long adminId) {
        LambdaQueryWrapper<RoleRelation> lqw = new LambdaQueryWrapper<>();
        lqw.eq(RoleRelation::getAdminId, adminId);
        List<RoleRelation> roleRelations = roleRelationMapper.selectList(lqw);
        return roleRelations.stream().map(RoleRelation::getRoleId).collect(Collectors.toList());
    }
}





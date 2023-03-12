package com.kanojo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kanojo.module.Role;
import com.kanojo.service.RoleService;
import com.kanojo.mapper.RoleMapper;
import org.springframework.stereotype.Service;


@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{

}





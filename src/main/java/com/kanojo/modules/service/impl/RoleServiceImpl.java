package com.kanojo.modules.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kanojo.modules.mapper.RoleMapper;
import com.kanojo.modules.model.Role;
import com.kanojo.modules.service.RoleService;
import org.springframework.stereotype.Service;


@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
        implements RoleService {
}





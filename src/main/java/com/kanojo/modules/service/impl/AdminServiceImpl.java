package com.kanojo.modules.service.impl;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kanojo.common.exception.MyException;
import com.kanojo.config.security.bean.AdminDetails;
import com.kanojo.config.security.bean.MyJWT;
import com.kanojo.modules.mapper.AdminMapper;
import com.kanojo.modules.model.Admin;
import com.kanojo.modules.model.RoleRelation;
import com.kanojo.modules.service.AdminService;
import com.kanojo.modules.service.RoleRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService, UserDetailsService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private RoleRelationService roleRelationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MyJWT myJWT;

    @CreateCache(name = "username_userDetails_", expire = 60, timeUnit = TimeUnit.MINUTES)
    private Cache<String, UserDetails> userCache;

    @Override
    public String login(String username, String password) {
        AdminDetails userDetails = loadUserByUsername(username);
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new MyException("用户名或者密码错误");
        }
        //将登录用户存进上下文对象
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //将登录成功的用户存入缓存
        userCache.put(userDetails.getUsername(), userDetails);
        //返回token
        return myJWT.createJWT(userDetails);
    }

    @Override
    public Admin getUserByUsername(String username) {
        LambdaQueryWrapper<Admin> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Admin::getUsername, username);
        return adminMapper.selectOne(lqw);
    }

    /**
     * 自定义验证
     */
    @Override
    public AdminDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = getUserByUsername(username);
        //用户没查到
        if (admin == null) {
            throw new MyException("用户名或者密码错误");
        }
        //状态禁用
        if (admin.getStatus().equals(0)) {
            throw new MyException("账号已被禁用");
        }
        List<Long> roleIds = roleRelationService
                .getRoleRelationByAdminId(admin.getId())
                .stream()
                .map(RoleRelation::getRoleId)
                .collect(Collectors.toList());
        return new AdminDetails(admin, roleIds);
    }
}





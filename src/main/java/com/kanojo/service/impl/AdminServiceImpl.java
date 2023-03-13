package com.kanojo.service.impl;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kanojo.config.security.bean.MyJWT;
import com.kanojo.domain.AdminDetails;
import com.kanojo.dto.LoginUserParam;
import com.kanojo.exception.MyException;
import com.kanojo.mapper.AdminMapper;
import com.kanojo.module.Admin;
import com.kanojo.module.Resource;
import com.kanojo.service.AdminService;
import com.kanojo.service.ResourceRelationService;
import com.kanojo.service.ResourceService;
import com.kanojo.service.RoleRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;


@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService, UserDetailsService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private RoleRelationService roleRelationService;

    @Autowired
    private ResourceRelationService resourceRelationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MyJWT myJWT;

    @CreateCache(name = "username_resources_", expire = 60, timeUnit = TimeUnit.MINUTES)
    private Cache<String, List<Resource>> resourceCache;

    @CreateCache(name = "id_userDetails", expire = 60, timeUnit = TimeUnit.MINUTES)
    private Cache<Long, AdminDetails> userCache;

    @Override
    public String login(LoginUserParam param) {
        AdminDetails userDetails = loadUserByUsername(param.getUsername());
        if (!passwordEncoder.matches(param.getPassword(), userDetails.getPassword())) {
            throw new MyException("用户名或者密码错误");
        }
        //将登录用户存进上下文对象
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //将登录成功的用户存入缓存
        userCache.put(userDetails.getAdmin().getId(), userDetails);
        //返回token
        return myJWT.createJWT(userDetails);
    }

    @Override
    public Admin getUserByUsername(String username) {
        LambdaQueryWrapper<Admin> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Admin::getUsername, username);
        return adminMapper.selectOne(lqw);
    }

    @Override
    public List<Resource> getResources(Long adminId) {
        //获取角色id数组
        List<Long> roleIdList = roleRelationService.getRoleIdList(adminId);
        //获取资源id数组（set防止数据库重复资源id）
        Set<Long> resourceIdList = resourceRelationService.getResourceIdList(roleIdList);
        //转为list集合传递获取全部资源
        return resourceService.getResources(new ArrayList<>(resourceIdList));
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
        //从缓存里面取
        List<Resource> resources = resourceCache.get(username);
        //缓存暂无数据
        if (resources == null) {
            resources = getResources(admin.getId());
            resourceCache.put(username, resources);
        }
        return new AdminDetails(admin, resources);
    }
}





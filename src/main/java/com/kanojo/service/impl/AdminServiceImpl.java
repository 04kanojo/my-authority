package com.kanojo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kanojo.config.security.bean.MyJWT;
import com.kanojo.domain.AdminDetails;
import com.kanojo.exception.MyException;
import com.kanojo.module.Admin;
import com.kanojo.dto.LoginUserParam;
import com.kanojo.module.Resource;
import com.kanojo.service.AdminService;
import com.kanojo.mapper.AdminMapper;
import com.kanojo.service.ResourceRelationService;
import com.kanojo.service.ResourceService;
import com.kanojo.service.RoleRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

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

    @Override
    public String login(LoginUserParam param) {
        //获取userDetails
        UserDetails userDetails = loadUserByUsername(param.getUsername(), param.getPassword());
        //将登录用户存进上下文对象
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //生成token返回
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

    @Override
    public UserDetails loadUserByUsername(String username, String password) {
        Admin admin = getUserByUsername(username);
        //用户没查到
        if (Objects.isNull(admin)) {
            throw new MyException("用户名或者密码错误");
        }
        //密码错误
        if (!passwordEncoder.matches(password, admin.getPassword())) {
            throw new MyException("用户名或者密码错误");
        }
        //状态禁用
        if (admin.getStatus().equals(0)) {
            throw new MyException("账号已被禁用");
        }
        List<Resource> resources = getResources(admin.getId());
        //资源列表为空
        if (resources.size() == 0) {
            throw new MyException("用户暂无资源可访问");
        }
        return new AdminDetails(admin, resources);
    }
}





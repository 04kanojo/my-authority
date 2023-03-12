package com.kanojo.service;

import com.alicp.jetcache.anno.Cached;
import com.kanojo.exception.MyException;
import com.kanojo.module.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kanojo.dto.LoginUserParam;
import com.kanojo.module.Resource;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.concurrent.TimeUnit;


public interface AdminService extends IService<Admin> {

    /**
     * 登录返回token
     */
    String login(LoginUserParam param);

    /**
     * 获取用户 根据用户名
     */
    @Cached(name = "user_", key = "username", expire = 30, timeUnit = TimeUnit.MINUTES)
    Admin getUserByUsername(String username);

    /**
     * 获取资源 根据用户id
     */
    @Cached(name = "resources_", key = "#adminId", expire = 30, timeUnit = TimeUnit.MINUTES)
    List<Resource> getResources(Long adminId);

    /**
     * 获取用户细节
     */
    @Cached(name = "userDetail_", key = "#username", expire = 30, timeUnit = TimeUnit.MINUTES)
    UserDetails loadUserByUsername(String userName, String password);
}

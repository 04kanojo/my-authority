package com.kanojo.modules.service;

import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kanojo.modules.model.Admin;

import java.util.concurrent.TimeUnit;


public interface AdminService extends IService<Admin> {

    /**
     * 登录返回token
     */
    String login(String username, String password);

    /**
     * 获取用户 根据用户名
     */
    @Cached(name = "username_admin_", expire = 30, timeUnit = TimeUnit.MINUTES)
    Admin getUserByUsername(String username);
}

package com.kanojo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kanojo.module.Admin;
import com.kanojo.module.Resource;

import java.util.List;


public interface AdminService extends IService<Admin> {

    /**
     * 登录返回token
     */
    String login(String username, String password);

    /**
     * 获取用户 根据用户名
     */
    Admin getUserByUsername(String username);

    /**
     * 获取资源 根据用户id
     */
    List<Resource> getResources(Long adminId);
}

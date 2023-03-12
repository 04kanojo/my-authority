package com.kanojo.controller;

import com.kanojo.common.Result;
import com.kanojo.dto.LoginUserParam;
import com.kanojo.exception.MyException;
import com.kanojo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/admin")
//CommonResult结果封装类泛型不传数据报黄(强迫症患者可加)
@SuppressWarnings("rawtypes")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    private Result login(@RequestBody LoginUserParam param) throws MyException {
        String token = adminService.login(param);
        if (Objects.isNull(token)) {
            return Result.validateFailed("用户名或密码错误");
        }
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        return Result.success(map, "登录成功");
    }
}

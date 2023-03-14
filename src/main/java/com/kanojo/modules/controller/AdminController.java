package com.kanojo.modules.controller;

import com.kanojo.common.exception.MyException;
import com.kanojo.common.result.Result;
import com.kanojo.modules.dto.LoginUserParam;
import com.kanojo.modules.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        String token = adminService.login(param.getUsername(), param.getPassword());
        if (Objects.isNull(token)) {
            return Result.validateFailed("用户名或密码错误");
        }
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        return Result.success(map, "登录成功");
    }

    @GetMapping("/hello")
    private String hello() {
        return "hello";
    }
}

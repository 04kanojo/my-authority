package com.kanojo.config.security.bean;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.signers.JWTSigner;
import cn.hutool.jwt.signers.JWTSignerUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class MyJWT {
    @Value("${jwt.key}")
    private String key;

    /**
     * 过期时间
     */
    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * 获取解析出来的数据
     */
    public Object parse(String token, String name) {
        JWT jwt = JWTUtil.parseToken(token);
        //获取负载
        return jwt.getPayload(name);
    }

    public String createJWT(AdminDetails userDetails) {
        //使用hs256算法根据用户id生成签名
        //公钥+私钥
        JWTSigner jwtSigner = JWTSignerUtil.hs256((key + userDetails.getAdmin().getId()).getBytes());

        DateTime now = DateTime.now();
        DateTime newTime = now.offsetNew(DateField.MINUTE, Math.toIntExact(expiration));
        Map<String, Object> payload = new HashMap<>();
        //签发时间
        payload.put(JWTPayload.ISSUED_AT, now);
        //过期时间
        payload.put(JWTPayload.EXPIRES_AT, newTime);
        //生效时间
        payload.put(JWTPayload.NOT_BEFORE, now);
        //载荷
        payload.put("username", userDetails.getUsername());
        //使用hutool生成token
        return JWTUtil.createToken(payload, jwtSigner);
    }
}

package com.kanojo.config.security.bean;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.signers.JWTSigner;
import cn.hutool.jwt.signers.JWTSignerUtil;
import com.kanojo.module.Admin;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

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

    private JWTSigner jwtSigner;

    /**
     * 获取解析出来的数据
     *
     * @param name 名字
     */
    public Object getValue(String name, String token) {
        JWT jwt = JWTUtil.parseToken(token);
        //设置签名
        jwt.setSigner(jwtSigner);
        //解析
        JWT parse = jwt.parse(token);
        //获取负载
        JWTPayload payload = parse.getPayload();
        return payload.getClaim(name);
    }

    public String createJWT(UserDetails userDetails) {
        //根据hs256算法生成签名
        JWTSigner jwtSigner = JWTSignerUtil.hs256(key.getBytes());
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
        payload.put("passWord", userDetails.getPassword());
        //使用hutool生成token
        return JWTUtil.createToken(payload, jwtSigner);
    }

    /**
     * 判断jwt有效性
     */
    public boolean verify(String token) {
        return JWTUtil.verify(token, jwtSigner);
    }

    /**
     * 初始化签名
     */
    @PostConstruct
    private void initSigner() {
        jwtSigner = JWTSignerUtil.hs256(key.getBytes());
    }
}

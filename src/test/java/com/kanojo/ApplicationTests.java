package com.kanojo;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.signers.JWTSigner;
import cn.hutool.jwt.signers.JWTSignerUtil;
import com.kanojo.config.security.bean.MyJWT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class ApplicationTests {

    @Autowired
    private MyJWT myJWT;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    @Test
//    void contextLoads() {
//
//        //根据hs256算法生成签名
//        JWTSigner jwtSigner = JWTSignerUtil.hs256("kanojo".getBytes());
//        DateTime now = DateTime.now();
//        DateTime newTime = now.offsetNew(DateField.MINUTE, 10);
//        Map<String, Object> payload = new HashMap<String, Object>();
//        //签发时间
//        payload.put(JWTPayload.ISSUED_AT, now);
//        //过期时间
//        payload.put(JWTPayload.EXPIRES_AT, newTime);
//        //生效时间
//        payload.put(JWTPayload.NOT_BEFORE, now);
//        User user = new User("bq", "gongbo");
//        payload.put("username", user.getName());
//        //载荷
////        payload.put("userName", "zhangsan");
////        payload.put("passWord", "666889");
//        String token = JWTUtil.createToken(payload, jwtSigner);
//        System.out.println(token);
//    }
//
//    @Test
//    void jeixi() {
//        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYmYiOjE2Nzg2MDgxNjIsImV4cCI6MTY3ODYwODc2MiwiaWF0IjoxNjc4NjA4MTYyLCJ1c2VybmFtZSI6ImJxIn0.hSBRqSEUb-TMpnt0_2hasbAzP-8D-2PvZv_4UeSnhd4";
//        JWT jwt = JWTUtil.parseToken(token);
//        JWTSigner jwtSigner = JWTSignerUtil.hs256("kanojo".getBytes());
//        //设置签名
//        jwt.setSigner(jwtSigner);
//        //解析
//        JWT parse = jwt.parse(token);
//        //获取负载
//        JWTPayload payload = parse.getPayload();
//        Object username = payload.getClaim("username");
//        System.out.println(username.toString());
//    }
//
//    @Test
//    void testMyJWT() {
//        String token = myJWT.createJWT(new User("gongbo", "123456"));
//        System.out.println(token);
//
//        Object username = myJWT.getValue("username", token);
//        System.out.println(username.toString());
//
//        boolean verify = myJWT.verify(token);
//        System.out.println(verify);
//    }
//
//    @Test
//    void testPassword() {
//        String gongbo = passwordEncoder.encode("gongbo");
//        System.out.println(gongbo);
//    }
}

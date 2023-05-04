package com.kanojo.config.security.bean;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.*;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@AllArgsConstructor
@NoArgsConstructor
public class JwtTool {
    @Value("${jwt.key}")
    private String key;

    private Algorithm algorithm;

    @Value("${jwt.expiration}")
    private Long expiration;

    private DecodedJWT decodedJWT;

    private JWTVerifier jwtVerifier;

    /**
     * 初始化(默认签名)
     */
    @PostConstruct
    public void init() {
        //签名
        algorithm = Algorithm.HMAC256(key);
        //过期时间(分钟)
        expiration = expiration * 60 * 1000;
        jwtVerifier = JWT.require(algorithm).build();
    }

    /**
     * 创建token
     *
     * @param leiName   类名
     * @param object    类的数据
     * @param algorithm 自定义签名
     * @return token
     */
    public <T> String createToken(Class<T> leiName, T object, String... algorithm) {
        if (algorithm.length != 0) {
            updateSign(algorithm[0]);
        }
        JWTCreator.Builder builder = JWT.create();
        //获取类名
        String name = leiName.toString();
        Map<String, T> map = new HashMap<>();
        //将数据存入
        map.put(name, object);
        builder.withClaim(name, map);
        return builder.withExpiresAt(new Date(System.currentTimeMillis() + expiration)).sign(this.algorithm);
    }

    /**
     * 判断token有效性
     */
    public boolean verify(String token) {
        try {
            decodedJWT = jwtVerifier.verify(token);
        } catch (AlgorithmMismatchException e) {
            log.error("token算法不一致");
            return false;
        } catch (InvalidClaimException e) {
            log.error("无效的token声明");
            return false;
        } catch (JWTDecodeException e) {
            log.error("token解码异常");
            return false;
        } catch (SignatureVerificationException e) {
            log.error("token签名无效");
            return false;
        } catch (TokenExpiredException e) {
            log.error("token已过期");
            return false;
        } catch (Exception e) {
            log.error("其他异常");
            return false;
        }
        return true;
    }

    /**
     * 获取全部负载
     */
    public Map<String, String> getClaims(String token) {
        decodedJWT = jwtVerifier.verify(token);
        Map<String, Claim> claimMap = decodedJWT.getClaims();
        Map<String, String> result = new HashMap<>();
        claimMap.forEach((key, value) -> result.put(key, value.asString()));
        return result;
    }

    /**
     * 加上私盐
     */
    public void updateSign(String msg) {
        StringBuilder sb = new StringBuilder(key);
        algorithm = Algorithm.HMAC256(sb.append(msg).toString());
        jwtVerifier = JWT.require(algorithm).build();
    }

    /**
     * 获取负载
     */
    public String getClaim(String token, String key) {
        decodedJWT = jwtVerifier.verify(token);
        return decodedJWT.getClaim(key).asString();
    }
}

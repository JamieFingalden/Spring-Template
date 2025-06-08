package com.fingalden.template.core.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * JwtUtil类提供了JWT（JSON Web Token）的生成和验证功能
 * 它包含两个主要功能：生成令牌和验证令牌并提取用户信息
 */
@Component
public class JwtUtil {
    // 定义JWT的密钥，注意：在生产环境中应该使用更安全的方式来存储和访问这个密钥
    @Value("${jwt.tokenSignKey}")
    private String SECRET;

    @Value("${jwt.tokenExpiration}")
    private long MS;

    // 定义JWT的过期时间，这里设置为一天（毫秒）
    private final long EXP_MS = 24 * 60 * 60 * 1000 * MS;

    /**
     * 生成JWT令牌
     *
     * @param username 用户名，作为JWT的主体部分
     * @return 生成的JWT令牌字符串
     */
    public String generateToken(String username) {
        return Jwts.builder()
               .setSubject(username) // 设置JWT的主体为用户名
               .setIssuedAt(new Date()) // 设置JWT的发行时间为当前时间
               .setExpiration(new Date(System.currentTimeMillis() + EXP_MS)) // 设置JWT的过期时间
               .signWith(SignatureAlgorithm.HS512, SECRET) // 使用HS512算法和密钥对JWT进行签名
               .compact(); // 生成紧凑的JWT字符串
    }

    /**
     * 验证JWT令牌并提取用户名
     *
     * @param token 需要验证的JWT令牌字符串
     * @return 如果令牌验证通过，则返回令牌中的用户名；否则可能抛出异常
     */
    public String validateAndGetUser(String token) {
        Claims c = Jwts.parser()
               .setSigningKey(SECRET) // 使用密钥解析JWT
               .parseClaimsJws(token).getBody(); // 验证JWT并获取负载部分
        return c.getSubject(); // 从负载中提取用户名
    }
}

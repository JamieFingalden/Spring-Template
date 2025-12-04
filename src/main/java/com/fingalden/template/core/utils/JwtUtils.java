package com.fingalden.template.core.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JwtUtils类提供了JWT（JSON Web Token）的生成、验证和解析功能
 * 支持从配置文件读取JWT密钥和过期时间，提供token刷新机制
 */
@Component
public class JwtUtils {

    /**
     * JWT密钥，用于签名和验证token
     * 从配置文件读取，建议使用至少256位的随机字符串
     */
    @Value("${jwt.secret:your-secret-key-change-in-production}")
    private String secret;

    /**
     * JWT过期时间（毫秒）
     * 从配置文件读取，默认7天
     */
    @Value("${jwt.expire:604800000}")
    private long expire;

    /**
     * JWT刷新过期时间（毫秒）
     * 从配置文件读取，默认30天
     */
    @Value("${jwt.refresh-expire:2592000000}")
    private long refreshExpire;

    /**
     * 获取JWT签名密钥
     * 将字符串密钥转换为Key对象，用于JWT的签名和验证
     *
     * @return Key对象
     */
    private Key getSigningKey() {
        // 使用Keys.hmacShaKeyFor方法创建密钥，该方法会根据密钥长度自动选择合适的算法
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * 生成JWT token
     * 包含用户ID、用户名、角色等信息，并设置过期时间
     *
     * @param userId 用户ID
     * @param username 用户名
     * @param role 用户角色
     * @return JWT token字符串
     */
    public String generateToken(Long userId, String username, String role) {
        // 创建自定义声明
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("role", role);

        // 生成token
        return Jwts.builder()
                .setClaims(claims) // 设置自定义声明
                .setSubject(username) // 设置主题（用户名）
                .setIssuedAt(new Date()) // 设置签发时间
                .setExpiration(new Date(System.currentTimeMillis() + expire)) // 设置过期时间
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // 设置签名密钥和算法
                .compact(); // 生成并返回token
    }

    /**
     * 生成刷新token
     * 刷新token用于获取新的访问token，过期时间更长
     *
     * @param userId 用户ID
     * @param username 用户名
     * @return 刷新token字符串
     */
    public String generateRefreshToken(Long userId, String username) {
        // 生成刷新token，只包含必要信息
        return Jwts.builder()
                .setSubject(username)
                .claim("userId", userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpire))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 解析JWT token，获取Claims对象
     * Claims包含了token中的所有声明信息
     *
     * @param token JWT token字符串
     * @return Claims对象
     * @throws ExpiredJwtException token已过期
     * @throws MalformedJwtException token格式错误
     * @throws UnsupportedJwtException token不支持
     * @throws IllegalArgumentException token参数为空
     */
    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey()) // 设置签名密钥
                .build()
                .parseClaimsJws(token) // 解析token
                .getBody(); // 获取Claims对象
    }

    /**
     * 验证JWT token是否有效
     * 验证包括：签名是否正确、是否过期、格式是否正确
     *
     * @param token JWT token字符串
     * @return boolean token是否有效
     */
    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (ExpiredJwtException e) {
            // token已过期
            return false;
        } catch (MalformedJwtException e) {
            // token格式错误
            return false;
        } catch (UnsupportedJwtException e) {
            // token不支持
            return false;
        } catch (IllegalArgumentException e) {
            // token参数为空
            return false;
        } catch (io.jsonwebtoken.security.SignatureException e) {
            // 签名错误
            return false;
        }
    }

    /**
     * 从token中获取用户名
     *
     * @param token JWT token字符串
     * @return String 用户名
     */
    public String getUsernameFromToken(String token) {
        Claims claims = parseToken(token);
        return claims.getSubject();
    }

    /**
     * 从token中获取用户ID
     *
     * @param token JWT token字符串
     * @return Long 用户ID
     */
    public Long getUserIdFromToken(String token) {
        Claims claims = parseToken(token);
        return claims.get("userId", Long.class);
    }

    /**
     * 从token中获取用户角色
     *
     * @param token JWT token字符串
     * @return String 用户角色
     */
    public String getRoleFromToken(String token) {
        Claims claims = parseToken(token);
        return claims.get("role", String.class);
    }

    /**
     * 获取token过期时间
     *
     * @param token JWT token字符串
     * @return Date 过期时间
     */
    public Date getExpirationFromToken(String token) {
        Claims claims = parseToken(token);
        return claims.getExpiration();
    }

    /**
     * 判断token是否即将过期
     * 如果token在指定时间内即将过期，则返回true
     *
     * @param token JWT token字符串
     * @param timeToExpire 即将过期的时间阈值（毫秒）
     * @return boolean token是否即将过期
     */
    public boolean isTokenExpiringSoon(String token, long timeToExpire) {
        Date expiration = getExpirationFromToken(token);
        long timeLeft = expiration.getTime() - System.currentTimeMillis();
        return timeLeft < timeToExpire;
    }
}


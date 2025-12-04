package com.fingalden.template.auth;

import com.fingalden.template.core.utils.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWT认证过滤器，用于拦截请求并进行JWT token验证
 * 继承OncePerRequestFilter以确保过滤器只对每个请求执行一次
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    
    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    /**
     * 从请求头中获取JWT token
     * 通常token会放在Authorization头中，格式为"Bearer {token}"
     *
     * @param request HttpServletRequest对象
     * @return String JWT token，如果不存在则返回null
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }

    /**
     * 过滤器的核心方法，用于处理每个请求
     * 1. 从请求中获取token
     * 2. 验证token的有效性
     * 3. 解析token获取用户名
     * 4. 加载用户信息
     * 5. 设置认证信息到SecurityContext
     *
     * @param request HttpServletRequest对象
     * @param response HttpServletResponse对象
     * @param filterChain FilterChain对象，用于继续过滤链
     * @throws IOException IO异常
     * @throws ServletException Servlet异常
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {
        String token = getTokenFromRequest(request);
        
        if (StringUtils.hasText(token)) {
            try {
                // 验证token是否有效
                if (jwtUtils.validateToken(token)) {
                    // 从token中获取用户名
                    String username = jwtUtils.getUsernameFromToken(token);
                    // 加载用户信息
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    // 创建认证对象
                    UsernamePasswordAuthenticationToken authentication = 
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    // 设置认证信息到SecurityContext
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    logger.debug("User '{}' authenticated successfully", username);
                } else {
                    logger.warn("Invalid JWT token: {}", token);
                }
            } catch (ExpiredJwtException e) {
                logger.warn("JWT token expired: {}", token);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"code\":401,\"message\":\"Token expired\"}");
                return;
            } catch (MalformedJwtException e) {
                logger.warn("Invalid JWT token format: {}", token);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"code\":401,\"message\":\"Invalid token format\"}");
                return;
            } catch (UnsupportedJwtException e) {
                logger.warn("Unsupported JWT token: {}", token);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"code\":401,\"message\":\"Unsupported token\"}");
                return;
            } catch (Exception e) {
                logger.error("JWT authentication failed: {}", e.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"code\":401,\"message\":\"Authentication failed\"}");
                return;
            }
        }
        
        // 继续执行过滤链
        filterChain.doFilter(request, response);
    }
}
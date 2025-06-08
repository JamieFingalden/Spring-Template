package com.fingalden.template.core.Filter;

import com.fingalden.template.core.utils.JwtUtil;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWT认证过滤器，用于拦截请求并进行JWT token验证
 * 继承OncePerRequestFilter以确保过滤器只对每个请求执行一次
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserDetailsService uds;

    /**
     * 核心过滤方法，对传入的请求进行JWT token验证
     *
     * @param req  HttpServletRequest对象，用于获取请求头中的JWT token
     * @param res  HttpServletResponse对象，用于处理响应
     * @param chain 过滤链，用于将请求传递到下一个过滤器或目标资源
     * @throws ServletException 如果发生Servlet异常
     * @throws IOException 如果发生IO异常
     */
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
        throws ServletException, IOException {
        // 获取请求头中的Authorization信息
        String h = req.getHeader("Authorization");
        // 检查Authorization头是否以"Bearer "开始，表明可能包含JWT token
        if (h != null && h.startsWith("Bearer ")) {
            // 提取token，去除"Bearer "前缀
            String token = h.substring(7);
            try {
                // 验证token有效性并获取用户名
                String u = jwtUtil.validateAndGetUser(token);
                // 根据用户名加载用户详细信息
                UserDetails ud = uds.loadUserByUsername(u);
                // 创建认证对象，设置用户权限，并将其存储在SecurityContext中
                var auth = new UsernamePasswordAuthenticationToken(ud, null, ud.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (JwtException e) {
                // token 无效则跳到异常处理链
                throw new RuntimeException(e);
            }
        }
        // 将请求传递到下一个过滤器或目标资源
        chain.doFilter(req, res);
    }
}

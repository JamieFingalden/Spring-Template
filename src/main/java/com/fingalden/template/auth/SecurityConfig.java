package com.fingalden.template.auth;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * Spring Security配置类，用于配置系统的安全策略
 * 包括认证、授权、会话管理、CORS、CSRF等
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) // 启用方法级安全注解
public class SecurityConfig {

    /**
     * 配置密码编码器，使用BCrypt算法进行密码加密
     * BCrypt是一种强大的单向哈希算法，能够有效防止彩虹表攻击
     *
     * @return BCryptPasswordEncoder实例
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 获取AuthenticationManager，用于处理认证请求
     * AuthenticationManager是Spring Security的核心接口，负责认证用户
     *
     * @param authConfig AuthenticationConfiguration实例
     * @return AuthenticationManager实例
     * @throws Exception 配置异常
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * 配置认证入口点，用于处理未认证请求
     * 当用户尝试访问受保护资源但未提供有效认证时，会调用此入口点
     *
     * @return BasicAuthenticationEntryPoint实例
     */
    @Bean
    public BasicAuthenticationEntryPoint authenticationEntryPoint() {
        BasicAuthenticationEntryPoint entryPoint = new BasicAuthenticationEntryPoint();
        entryPoint.setRealmName("Spring Template API");
        return entryPoint;
    }

    /**
     * 配置访问拒绝处理器，用于处理无权访问请求
     * 当用户尝试访问其没有权限的资源时，会调用此处理器
     *
     * @return AccessDeniedHandler实例
     */
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            response.getWriter().write("{\"code\":403,\"message\":\"Access Denied\"}");
        };
    }

    /**
     * 配置SecurityFilterChain，定义系统的安全策略
     * 包括认证、授权、会话管理、CORS、CSRF等
     *
     * @param http HttpSecurity实例
     * @param jwtFilter JWT认证过滤器
     * @return SecurityFilterChain实例
     * @throws Exception 配置异常
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           JwtAuthenticationFilter jwtFilter) throws Exception {
        http
            // 禁用CSRF保护，因为我们使用JWT进行认证
            .csrf().disable()
            
            // 配置CORS，使用自定义的CORS配置
            .cors()
            .and()
            
            // 配置会话管理，使用无状态会话
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            
            // 配置认证入口点和访问拒绝处理器
            .exceptionHandling()
            .authenticationEntryPoint(authenticationEntryPoint())
            .accessDeniedHandler(accessDeniedHandler())
            .and()
            
            // 配置授权规则
            .authorizeHttpRequests()
            // 允许所有OPTIONS请求，用于预检请求
            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            // 允许访问静态资源
            .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/doc.html", "/webjars/**").permitAll()
            // 允许访问认证相关接口
            .requestMatchers("/api/user/login", "/api/user/register", "/api/user/refresh-token").permitAll()
            // 允许访问健康检查接口
            .requestMatchers("/actuator/health", "/health").permitAll()
            // 所有其他请求需要认证
            .anyRequest().authenticated();

        // 添加JWT认证过滤器，在UsernamePasswordAuthenticationFilter之前执行
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
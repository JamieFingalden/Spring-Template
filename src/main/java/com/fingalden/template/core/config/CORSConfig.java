package com.fingalden.template.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

/**
 * 全局CORS配置类，用于配置跨域请求
 * 跨域资源共享（CORS）是一种机制，它使用额外的HTTP头来告诉浏览器
 * 允许一个用户代理在具有不同来源（域名）的网页上访问选定的资源
 */
@Configuration
public class CORSConfig {

    /**
     * 允许的来源模式，从配置文件读取
     */
    @Value("${cors.allowed-origin-patterns:*}")
    private List<String> allowedOriginPatterns;

    /**
     * 允许的请求头，从配置文件读取
     */
    @Value("${cors.allowed-headers:*}")
    private List<String> allowedHeaders;

    /**
     * 允许的HTTP方法，从配置文件读取
     */
    @Value("${cors.allowed-methods:*}")
    private List<String> allowedMethods;

    /**
     * 暴露的响应头，从配置文件读取
     */
    @Value("${cors.exposed-headers:}")
    private List<String> exposedHeaders;

    /**
     * 是否允许发送Cookie，从配置文件读取
     */
    @Value("${cors.allow-credentials:true}")
    private boolean allowCredentials;

    /**
     * 预检请求的缓存时间，从配置文件读取
     */
    @Value("${cors.max-age:3600}")
    private long maxAge;

    /**
     * 创建并配置CorsFilter Bean
     * 
     * @return 配置好的CorsFilter实例
     */
    @Bean
    public CorsFilter corsFilter() {
        // 创建CORS配置对象
        CorsConfiguration config = new CorsConfiguration();
        
        // 设置是否允许发送Cookie
        config.setAllowCredentials(allowCredentials);
        
        // 添加允许的来源模式
        for (String originPattern : allowedOriginPatterns) {
            if (!originPattern.isEmpty()) {
                config.addAllowedOriginPattern(originPattern);
            }
        }
        
        // 添加允许的请求头
        for (String header : allowedHeaders) {
            if (!header.isEmpty()) {
                config.addAllowedHeader(header);
            }
        }
        
        // 添加允许的HTTP方法
        for (String method : allowedMethods) {
            if (!method.isEmpty()) {
                config.addAllowedMethod(method);
            }
        }
        
        // 添加暴露的响应头
        for (String exposedHeader : exposedHeaders) {
            if (!exposedHeader.isEmpty()) {
                config.addExposedHeader(exposedHeader);
            }
        }
        
        // 设置预检请求的缓存时间
        config.setMaxAge(maxAge);
        
        // 创建UrlBasedCorsConfigurationSource实例
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        
        // 注册CORS配置，适用于所有路径
        source.registerCorsConfiguration("/**", config);
        
        // 创建并返回CorsFilter实例
        return new CorsFilter(source);
    }
}


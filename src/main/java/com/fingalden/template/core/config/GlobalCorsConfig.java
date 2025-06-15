package com.fingalden.template.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 全局CORS配置类，用于配置跨域请求
 * 跨域资源共享（CORS）是一种机制，它使用额外的HTTP头来告诉浏览器
 * 允许一个用户代理在具有不同来源（域名）的网页上访问选定的资源
 */
@Configuration
public class GlobalCorsConfig implements WebMvcConfigurer {

    // 允许跨域请求的来源
    @Value("${cors.allowed-origins}")
    private String[] allowedOrigins;

    // 是否允许携带身份信息的请求
    @Value("${cors.allow-credentials}")
    private boolean allowCredentials;

    // 预检请求的缓存时间
    @Value("${cors.max-age}")
    private long maxAge;

    /**
     * 添加CORS映射配置
     * 此方法配置了跨域请求的规则，包括允许的来源、HTTP方法、头部、是否允许身份信息以及预检请求的缓存时间
     *
     * @param registry CORS注册表，用于添加CORS映射
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns(allowedOrigins)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD")
                .allowedHeaders("*")
                .allowCredentials(allowCredentials)
                .maxAge(maxAge);
    }
}

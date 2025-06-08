package com.fingalden.template.core.config;

import io.github.revolution.core.constant.ApiRouterConstants;
import io.github.revolution.core.interceptor.AuthInterceptor;
import io.github.revolution.core.interceptor.TokenParseInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器配置
 *
 * @Author YouZhi
 * @Date 2023 - 09 - 11 - 8:55
 */
@Component
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {


    private final AuthInterceptor authInterceptor;


    private final TokenParseInterceptor tokenParseInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {


        registry.addInterceptor(tokenParseInterceptor)
                //拦截前台用户相关请求接口
                .addPathPatterns(ApiRouterConstants.API_BACK_URL_PREFIX + "/**")
                .addPathPatterns(ApiRouterConstants.API_FRONT_URL_PREFIX + "/**")
                .excludePathPatterns(ApiRouterConstants.API_BACK_USER_URL_PREFIX + "/login")
                .order(0);


    }


}

package com.currency.teamflow.global.config;

import com.currency.teamflow.global.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**") // 모든 경로에 대해 적용
                .excludePathPatterns("/static/**", "/public/**"); // 정적 리소스 제외
    }

}


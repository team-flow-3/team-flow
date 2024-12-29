package com.currency.teamflow.global.config;

import com.currency.teamflow.global.interceptor.LoginInterceptor;
import com.currency.teamflow.global.interceptor.MemberRoleInterceptor;
import com.currency.teamflow.global.interceptor.UserRoleInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final LoginInterceptor loginInterceptor;
    private final UserRoleInterceptor userRoleInterceptor;
    private final MemberRoleInterceptor memberRoleInterceptor;

    public WebConfig(LoginInterceptor loginInterceptor,
                     UserRoleInterceptor userRoleInterceptor,
                     MemberRoleInterceptor memberRoleInterceptor
                     ) {
        this.loginInterceptor = loginInterceptor;
        this.userRoleInterceptor = userRoleInterceptor;
        this.memberRoleInterceptor = memberRoleInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 로그인 인터셉터
        // registry.addInterceptor(loginInterceptor);

        // 유저 권한 인터셉터
        registry.addInterceptor(userRoleInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/users/login", "/users/register");

        // 멤버 권한 인터셉터
        registry.addInterceptor(memberRoleInterceptor);
    }
}


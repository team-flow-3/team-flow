package com.currency.teamflow.global.interceptor;

import com.currency.teamflow.domain.user.entity.User;
import com.currency.teamflow.global.annotation.CheckUserRole;
import com.currency.teamflow.global.enums.Auth;
import com.currency.teamflow.global.error.errorcode.ErrorCode;
import com.currency.teamflow.global.error.exception.CustomException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;

@Slf4j
@Component
public class UserRoleInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            CheckUserRole checkUserRole = handlerMethod.getMethodAnnotation(CheckUserRole.class);

            // 어노테이션이 없는 경우, 모든 사용자 허용
            if (checkUserRole == null) {
                return true;
            }

            // 세션 확인
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("user") == null) {
                throw new CustomException(ErrorCode.UNAUTHORIZED);
            }

            // 사용자 권한 확인
            User user = (User) session.getAttribute("user");
            Auth[] requiredAuthorities = checkUserRole.requiredAuthorities();

            if (!Arrays.asList(requiredAuthorities).contains(user.getAuth())) {
                log.warn("UserRoleInterceptor: 권한 없음 - 사용자 권한: {}, 필요한 권한: {}",
                        user.getAuth(), Arrays.toString(requiredAuthorities));
                throw new CustomException(ErrorCode.FORBIDDEN_PERMISSION);
            }
        }

        return true;
    }
}



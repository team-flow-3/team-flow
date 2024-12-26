package com.currency.teamflow.global.interceptor;

import com.currency.teamflow.global.error.errorcode.ErrorCode;
import com.currency.teamflow.global.error.exception.CustomException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    private static final String[] WHITE_LIST = {"/users/login", "/users/register"};

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String requestURI = request.getRequestURI();

        // 화이트리스트에 포함된 요청은 바로 통과
        if (isWhiteList(requestURI)) {
            return true;
        }

        // 세션 확인
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        return true; // 요청 처리 허용
    }

    private boolean isWhiteList(String requestURI) {
        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);
    }
}

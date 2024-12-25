package com.currency.teamflow.global.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

@Slf4j
public class UserFilter implements Filter {

    private static final String[] WHITE_LIST = {"/users/login", "/users/register"};

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        // uri 로그를 찍기위해 설정
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        //HTTP 요청 정보 가져오기
        String requestURI = httpServletRequest.getRequestURI();

        // 화이트리스트로 로그인, 회원가입 시에는 허용
        if (!isWhiteList(requestURI)) {
            HttpSession session = httpServletRequest.getSession(false);
            log.info(requestURI);

            // 세션이 없으면 예외 발생
            if (session == null || session.getAttribute("user") == null) {
                throw new RuntimeException("로그인 해주세요");
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    // 화이트리스트 확인 로직
    private boolean isWhiteList(String requestURI) {
        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);
    }

}

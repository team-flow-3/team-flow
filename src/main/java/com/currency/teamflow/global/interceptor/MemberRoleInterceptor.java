package com.currency.teamflow.global.interceptor;

import com.currency.teamflow.domain.user.entity.User;
import com.currency.teamflow.domain.user.entity.WorkspaceUser;
import com.currency.teamflow.global.annotation.CheckMemberRole;
import com.currency.teamflow.global.config.auth.UserDetailsImpl;
import com.currency.teamflow.global.enums.Role;
import com.currency.teamflow.global.error.errorcode.ErrorCode;
import com.currency.teamflow.global.error.exception.CustomException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;

@Slf4j
@Component
public class MemberRoleInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true; // 핸들러가 메서드가 아닌 경우 허용
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        CheckMemberRole checkMemberRole = handlerMethod.getMethodAnnotation(CheckMemberRole.class);

        // 어노테이션이 없는 경우 모든 요청 허용
        if (checkMemberRole == null) {
            return true;
        }

        // 인증 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 인증 정보가 없으면 예러
        if (authentication == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        // 인증 정보 내의 유저 정보 가져오기
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User loginUser = userDetails.getUser();

        // 세션 확인
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("workspaceUser") == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED); // 세션에 필요한 정보가 없으면 예외 발생
        }

        // 세션에서 로그인 사용자와 선택된 워크스페이스 정보 가져오기
        WorkspaceUser workspaceUser = (WorkspaceUser) session.getAttribute("workspaceUser");

        // 로그인 사용자가 선택한 워크스페이스에 속해 있는지 검증
        boolean isUserInWorkspace = loginUser.getWorkspaceUsers().stream()
                .anyMatch(wu -> wu.getWorkspace().getId().equals(workspaceUser.getWorkspace().getId()));

        if (!isUserInWorkspace) {
            throw new CustomException(ErrorCode.FORBIDDEN_PERMISSION); // 선택된 워크스페이스와 로그인 사용자 불일치
        }

        // 역할 검증
        Role[] requiredRoles = checkMemberRole.requiredRoles();
        if (!Arrays.asList(requiredRoles).contains(workspaceUser.getRole())) {
            throw new CustomException(ErrorCode.FORBIDDEN_PERMISSION); // 역할 불일치
        }

        return true; // 요청 허용
    }
}






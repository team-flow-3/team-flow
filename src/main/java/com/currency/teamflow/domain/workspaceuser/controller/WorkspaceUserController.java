package com.currency.teamflow.domain.workspaceuser.controller;

import com.currency.teamflow.domain.workspaceuser.dto.WorkspaceUserResponseDto;
import com.currency.teamflow.domain.workspaceuser.service.WorkspaceUserService;
import com.currency.teamflow.global.annotation.CheckUserRole;
import com.currency.teamflow.global.enums.Auth;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class WorkspaceUserController {

    private final WorkspaceUserService workspaceUserService;

    public WorkspaceUserController (WorkspaceUserService workspaceUserService) {

        this.workspaceUserService = workspaceUserService;
    }

    /**
     * ADMIN이 특정 워크스페이스의 사용자를 WORKSPACE_ADMIN으로 설정
     * @param workspaceId 워크스페이스 ID
     * @param userId      사용자 ID
     * @return WorkspaceUserResponseDto
     */
    @CheckUserRole(requiredAuthorities = {Auth.ADMIN}) // ADMIN만 접근 가능
    @PostMapping("/admin/workspaces/{workspaceId}/users/{userId}")
    public ResponseEntity<WorkspaceUserResponseDto> assignWorkspaceAdmin(
            @PathVariable Long workspaceId,
            @PathVariable Long userId) {

        WorkspaceUserResponseDto responseDto = workspaceUserService.assignWorkspaceAdmin(workspaceId, userId);

        return ResponseEntity.ok(responseDto);
    }

    /**
     * ADMIN이 특정 워크스페이스 관리자를 삭제
     * @param workspaceUserId 워크스페이스 사용자 ID
     * @return 삭제 완료 메시지
     */
    @CheckUserRole(requiredAuthorities = {Auth.ADMIN}) // ADMIN만 접근 가능
    @DeleteMapping("/admin/workspaceUser/{workspaceUserId}")
    public ResponseEntity<String> deleteWorkspaceAdmin(@PathVariable Long workspaceUserId) {

        workspaceUserService.deleteWorkspaceAdmin(workspaceUserId);

        return ResponseEntity.ok("워크스페이스 관리자 삭제 완료");
    }
}


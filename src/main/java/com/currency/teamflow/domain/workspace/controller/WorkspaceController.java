package com.currency.teamflow.domain.workspace.controller;

import com.currency.teamflow.domain.user.entity.User;
import com.currency.teamflow.domain.workspace.dto.WorkspaceInviteRequestDto;
import com.currency.teamflow.domain.workspace.dto.WorkspaceInviteResponseDto;
import com.currency.teamflow.domain.workspace.dto.WorkspaceRequestDto;
import com.currency.teamflow.domain.workspace.dto.WorkspaceResponseDto;
import com.currency.teamflow.domain.workspace.service.WorkspaceService;
import com.currency.teamflow.global.annotation.CheckMemberRole;
import com.currency.teamflow.global.annotation.CheckUserRole;
import com.currency.teamflow.global.enums.Auth;
import com.currency.teamflow.global.enums.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping
public class WorkspaceController {

	private final WorkspaceService workspaceService;

	/**
	 * 워크스페이스 생성 API
	 * - 관리자 전용
	 */
	@CheckUserRole(requiredAuthorities = {Auth.ADMIN})
	@PostMapping("/workspaces")
	public ResponseEntity<WorkspaceResponseDto> createWorkspace(
		@RequestBody WorkspaceRequestDto workspaceRequestDto) {

		WorkspaceResponseDto workspaceResponseDto = workspaceService.createWorkspace(
			workspaceRequestDto.getWorkspaceName(),
			workspaceRequestDto.getWorkspaceExplanation()
		);

		return new ResponseEntity<>(workspaceResponseDto, HttpStatus.CREATED);
	};

	/**
	 * 워크스페이스 멤버 초대 API
	 * - 워크스페이스 관리자 전용
	 */
	@CheckMemberRole(requiredRoles = {Role.WORKSPACE_ADMIN})
	@PostMapping("/workspaces/{workspaceId}/invite/users/{userId}")
	public ResponseEntity<WorkspaceInviteResponseDto> inviteUserToWorkspace(
		@PathVariable Long workspaceId, @PathVariable Long userId,
		@RequestBody WorkspaceInviteRequestDto workspaceInviteRequestDto,
		HttpServletRequest httpServletRequest){

		HttpSession httpSession = httpServletRequest.getSession(false);//session이 존재하지 않으면 기존 세션 반환
		User loginedUser = (User) httpSession.getAttribute("user");

		WorkspaceInviteResponseDto workspaceInviteResponseDto = workspaceService.inviteUserToWorkspace(
			loginedUser,
			workspaceId,
			userId,
			workspaceInviteRequestDto
		);

		return new ResponseEntity<>(workspaceInviteResponseDto, HttpStatus.OK);
	};
}

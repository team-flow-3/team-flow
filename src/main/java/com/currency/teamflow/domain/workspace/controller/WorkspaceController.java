package com.currency.teamflow.domain.workspace.controller;

import com.currency.teamflow.domain.user.entity.User;
import com.currency.teamflow.domain.workspace.dto.UserWorkspaceListResponseDto;
import com.currency.teamflow.domain.workspace.dto.WorkspaceAdminResponseDto;
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
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
	 * TODO : n+1 발생 전체적으로 쿼리 개선하기
	 */

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
	}

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
	}

	/**
	 * 유저 워크스페이스 조회 API
	 * - 관리자 전용
	 */
	@CheckUserRole(requiredAuthorities = {Auth.ADMIN})
	@GetMapping("/users/{userId}/workspaces")
	public ResponseEntity<WorkspaceAdminResponseDto> adminSearchUserWorkspace(@PathVariable Long userId){
		WorkspaceAdminResponseDto workspaceAdminResponseDto = workspaceService.adminSearchUserWorkspace(userId);

		return new ResponseEntity<>(workspaceAdminResponseDto, HttpStatus.OK);
	}

	/**
	 * 본인 워크스페이스 조회 API
	 * - 로그인한 유저 전용
	 */
	@GetMapping("/workspaces")
	public ResponseEntity<List<UserWorkspaceListResponseDto>> userSearchWorkspace(
		HttpServletRequest httpServletRequest){
		HttpSession httpSession = httpServletRequest.getSession(false);//session이 존재하지 않으면 기존 세션 반환
		User loginedUser = (User) httpSession.getAttribute("user");

		List<UserWorkspaceListResponseDto> userWorkspaceListResponseDto = workspaceService.userSearchWorkspace(loginedUser);

		return new ResponseEntity<>(userWorkspaceListResponseDto, HttpStatus.OK);
	}

	/**
	 * 워크스페이스 수정 API
	 * - 워크스페이스 관리자 전용
	 */
	@CheckMemberRole(requiredRoles = {Role.WORKSPACE_ADMIN})
	@PatchMapping("/workspaces/{workspaceId}")
	public ResponseEntity<WorkspaceResponseDto> updateWorkspace(
		@PathVariable Long workspaceId,
		@RequestBody WorkspaceRequestDto WorkspaceRequestDto,
		HttpServletRequest httpServletRequest){

		HttpSession httpSession = httpServletRequest.getSession(false);
		User loginedUser = (User) httpSession.getAttribute("user");

		WorkspaceResponseDto workspaceResponseDto = workspaceService.updateWorkspace(loginedUser, workspaceId, WorkspaceRequestDto);

		return new ResponseEntity<>(workspaceResponseDto, HttpStatus.OK);
	}

	/**
	 * 워크스페이스 삭제 API
	 * - 워크스페이스 관리자 전용
	 */
	@CheckMemberRole(requiredRoles = {Role.WORKSPACE_ADMIN})
	@DeleteMapping("/workspaces/{workspaceId}")
	public ResponseEntity<String> deleteWorkspace(
		@PathVariable Long workspaceId,
		HttpServletRequest httpServletRequest){

		HttpSession httpSession = httpServletRequest.getSession(false);
		User loginedUser = (User) httpSession.getAttribute("user");

		workspaceService.deleteWorkspace(workspaceId, loginedUser);

		return new ResponseEntity<>("워크스페이스가 삭제되었습니다." , HttpStatus.OK);
	}

}

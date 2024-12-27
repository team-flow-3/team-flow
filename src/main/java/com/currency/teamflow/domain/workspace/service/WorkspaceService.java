package com.currency.teamflow.domain.workspace.service;

import com.currency.teamflow.domain.user.dto.UserRegisterResponseDto;
import com.currency.teamflow.domain.user.entity.User;
import com.currency.teamflow.domain.user.entity.WorkspaceUser;
import com.currency.teamflow.domain.user.repository.UserRepository;
import com.currency.teamflow.domain.workspace.dto.UserWorkspaceListResponseDto;
import com.currency.teamflow.domain.workspace.dto.WorkspaceAdminResponseDto;
import com.currency.teamflow.domain.workspace.dto.WorkspaceInviteRequestDto;
import com.currency.teamflow.domain.workspace.dto.WorkspaceInviteResponseDto;
import com.currency.teamflow.domain.workspace.dto.WorkspaceResponseDto;
import com.currency.teamflow.domain.workspace.entity.Workspace;
import com.currency.teamflow.domain.workspace.repository.WorkspaceRepository;
import com.currency.teamflow.domain.workspaceuser.repository.WorkspaceUserRepository;
import com.currency.teamflow.global.enums.Role;
import com.currency.teamflow.global.error.errorcode.ErrorCode;
import com.currency.teamflow.global.error.exception.CustomException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class WorkspaceService {

	private final WorkspaceRepository workspaceRepository;
	private final UserRepository userRepository;
	private final WorkspaceUserRepository workspaceUserRepository;


	public WorkspaceService(WorkspaceRepository workspaceRepository, UserRepository userRepository,
		WorkspaceUserRepository workspaceUserRepository) {
		this.workspaceRepository = workspaceRepository;
		this.userRepository = userRepository;
		this.workspaceUserRepository = workspaceUserRepository;
	}

	/**
	 * 워크스페이스 생성 API
	 * - 관리자 전용
	 */
	@Transactional
	public WorkspaceResponseDto createWorkspace(String workspaceName, String workspaceExplanation) {
		Workspace workspace = new Workspace(workspaceName, workspaceExplanation);
		//워크스페이스 저장
		Workspace savedWorkspace = workspaceRepository.save(workspace);

		return WorkspaceResponseDto.toDto(savedWorkspace);
	}

	/**
	 * 워크스페이스 멤버 초대 API
	 * - 워크스페이스 관리자 전용
	 */
	@Transactional
	public WorkspaceInviteResponseDto inviteUserToWorkspace(User loginedUser, Long workspaceId, Long userId, WorkspaceInviteRequestDto workspaceInviteRequestDto) {
		//로그인 한 유저와 초대하는 유저의 id가 같으면 예외처리
		if(loginedUser.getId().equals(userId)){
			throw new CustomException(ErrorCode.NOT_INVITE_YOURSELF);
		}
		
		//워크스페이스 보드가 존재하는지 확인
		Workspace workspace = workspaceRepository.findByIdOrElseThrow(workspaceId);
		//유저 id랑 이메일 검증
		User user = userRepository.findUserByIdAndEmailOrElseThrow(userId, workspaceInviteRequestDto.getEmail());

		//워크스페이스에 초대된 유저인지 중복 검증
		if(workspaceUserRepository.existsByWorkspaceIdAndUserId(workspaceId, userId)){
			throw new CustomException(ErrorCode.USER_ALREADY_INVITED);
		}

		//유저 초대 & 저장
		WorkspaceUser workspaceUserInvite = new WorkspaceUser(user, workspace, Role.READ_ONLY);
		WorkspaceUser savedWorkspaceUserInvite = workspaceUserRepository.save(workspaceUserInvite);

		return WorkspaceInviteResponseDto.toDto(savedWorkspaceUserInvite.getWorkspace(), user.getId());
	}

	/**
	 * 유저 워크스페이스 조회 API
	 * - 관리자 전용
	 */
	public WorkspaceAdminResponseDto adminSearchUserWorkspace(Long userId) {
		//유저 존재하는지 확인
		User user = userRepository.findByUserOrElseThrow(userId);
		//유저 워크스페이스 리스트 가져오기
		List<UserWorkspaceListResponseDto> userWorkspaceListResponseDto =  workspaceRepository.findAllWorkspaceByUserId(userId);

		return WorkspaceAdminResponseDto.toDto(user, userWorkspaceListResponseDto);
	}
}

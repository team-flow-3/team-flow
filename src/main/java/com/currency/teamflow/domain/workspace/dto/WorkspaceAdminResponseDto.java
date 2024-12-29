package com.currency.teamflow.domain.workspace.dto;

import com.currency.teamflow.domain.user.entity.User;
import com.currency.teamflow.global.enums.Auth;
import java.util.List;
import lombok.Getter;

@Getter
public class WorkspaceAdminResponseDto {

	private final Long userId;//사용자 id

	private final String email;//사용자 이메일

	private final String nickName;//사용자 닉네임

	private final Auth auth;//사용자 권한

	private List<UserWorkspaceListResponseDto> workspaceInfo;//워크스페이스 정보

	public WorkspaceAdminResponseDto(Long userId, String email, String nickName, Auth auth,  List<UserWorkspaceListResponseDto> workspaceInfo) {
		this.userId = userId;
		this.email = email;
		this.nickName = nickName;
		this.auth = auth;
		this.workspaceInfo = workspaceInfo;
	}

	/**
	 * 관리자 - 유저 워크스페이스 조회
	 */
	public static WorkspaceAdminResponseDto toDto(User user,List<UserWorkspaceListResponseDto> userWorkspaceListResponseDto) {
		return new WorkspaceAdminResponseDto(
			user.getId(),
			user.getEmail(),
			user.getNickName(),
			user.getAuth(),
			userWorkspaceListResponseDto
		);
	}

}

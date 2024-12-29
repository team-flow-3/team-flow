package com.currency.teamflow.domain.workspace.dto;

import com.currency.teamflow.domain.workspace.entity.Workspace;
import com.currency.teamflow.global.enums.Role;
import java.util.List;
import lombok.Getter;

@Getter
public class UserWorkspaceListResponseDto {

	private final Long workspaceId;//워크스페이스 아이디

	private final String workspaceName;//워크스페이스 이름

	private final String workspaceExplanation;//워크스페이스 설명

	private final Role role;//권한

	public UserWorkspaceListResponseDto(Long workspaceId, String workspaceName, String workspaceExplanation,
		Role role) {
		this.workspaceId = workspaceId;
		this.workspaceName = workspaceName;
		this.workspaceExplanation = workspaceExplanation;
		this.role = role;
	}

	public UserWorkspaceListResponseDto(Long workspaceId, List<UserWorkspaceListResponseDto> userWorkspaceListResponseDto,
		String workspaceName, String workspaceExplanation, Role role) {
		this.workspaceId = workspaceId;
		this.workspaceName = workspaceName;
		this.workspaceExplanation = workspaceExplanation;
		this.role = role;
	}

	public static UserWorkspaceListResponseDto toDto(Workspace workspace, Role role) {
		return new UserWorkspaceListResponseDto(
			workspace.getId(),
			workspace.getWorkspaceName(),
			workspace.getWorkspaceExplanation(),
			role
		);
	}

}

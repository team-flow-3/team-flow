package com.currency.teamflow.domain.workspace.dto;

import com.currency.teamflow.domain.workspace.entity.Workspace;
import com.currency.teamflow.global.enums.Role;
import lombok.Getter;

@Getter
public class UserWorkspaceListResponseDto {

	private final String workspaceName;//워크스페이스 이름

	private final String workspaceExplanation;//워크스페이스 설명

	private final Role role;//권한

	public UserWorkspaceListResponseDto(String workspaceName, String workspaceExplanation,
		Role role) {
		this.workspaceName = workspaceName;
		this.workspaceExplanation = workspaceExplanation;
		this.role = role;
	}

	//워크스페이스 생성
	public static UserWorkspaceListResponseDto toDto(Workspace workspace, Role role) {
		return new UserWorkspaceListResponseDto(
			workspace.getWorkspaceName(),
			workspace.getWorkspaceExplanation(),
			role
		);
	}
}

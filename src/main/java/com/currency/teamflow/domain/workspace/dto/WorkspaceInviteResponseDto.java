package com.currency.teamflow.domain.workspace.dto;

import com.currency.teamflow.domain.workspace.entity.Workspace;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class WorkspaceInviteResponseDto {

	private final Long id;//워크스페이스 id

	private final Long userId; //사용자 id

	private final String workspaceName;//워크스페이스 이름

	private final String workspaceExplanation;//워크스페이스 설명

	private final LocalDateTime createdAt;//생성일

	private final LocalDateTime modifiedAt;//수정일

	public WorkspaceInviteResponseDto(Long id, Long userId, String workspaceName, String workspaceExplanation,
		LocalDateTime createdAt, LocalDateTime modifiedAt) {
		this.id = id;
		this.userId = userId;
		this.workspaceName = workspaceName;
		this.workspaceExplanation = workspaceExplanation;
		this.createdAt = createdAt;
		this.modifiedAt = modifiedAt;
	}

	//워크스페이스 생성
	public static WorkspaceInviteResponseDto toDto(Workspace workspace, Long userId) {
		return new WorkspaceInviteResponseDto(
			workspace.getId(),
			userId,
			workspace.getWorkspaceName(),
			workspace.getWorkspaceExplanation(),
			workspace.getCreatedAt(),
			workspace.getModifiedAt()
		);
	}

}

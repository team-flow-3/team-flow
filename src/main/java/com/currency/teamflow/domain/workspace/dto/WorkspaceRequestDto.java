package com.currency.teamflow.domain.workspace.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class WorkspaceRequestDto {

	@NotNull(message = "워크스페이스 이름은 필수 입력값입니다.")
	private final String workspaceName;//워크스페이스 이름

	@NotNull(message = "워크스페이스 설명은 필수 입력값입니다.")
	private final String workspaceExplanation;//워크스페이스 설명

	public WorkspaceRequestDto(String workspaceName, String workspaceExplanation) {
		this.workspaceName = workspaceName;
		this.workspaceExplanation = workspaceExplanation;
	}
}

package com.currency.teamflow.domain.workspace.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class WorkspaceInviteRequestDto {

	@NotNull(message = "초대할 사용자 이메일은 필수 입력값입니다.")
	private final String email;//워크스페이스 이름

	public WorkspaceInviteRequestDto(String email) {
		this.email = email;
	}
}

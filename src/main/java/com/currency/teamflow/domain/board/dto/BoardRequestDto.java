package com.currency.teamflow.domain.board.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class BoardRequestDto {

	@NotNull
	private final Long workspaceId;//워크스페이스 id(외래키)

	@NotNull(message = "보드 제목은 필수 입력값입니다.")
	private final String boardTitle;//보드 제목

	@NotNull(message = "보드 배경색은 필수 입력값입니다.")
	private final String boardBackgroundColor;//보드 배경색

	private BoardRequestDto(Long workspaceId, String boardTitle, String boardBackgroundColor){
		this.workspaceId = workspaceId;
		this.boardTitle = boardTitle;
		this.boardBackgroundColor = boardBackgroundColor;
	}
}

package com.currency.teamflow.domain.board.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class BoardRequestDto {

	@NotNull(message = "보드 제목은 필수 입력값입니다.")
	private final String boardTitle;//보드 제목

	@NotNull(message = "보드 배경색은 필수 입력값입니다.")
	private final String boardBackgroundColor;//보드 배경색

	private BoardRequestDto(String boardTitle, String boardBackgroundColor){
		this.boardTitle = boardTitle;
		this.boardBackgroundColor = boardBackgroundColor;
	}
}

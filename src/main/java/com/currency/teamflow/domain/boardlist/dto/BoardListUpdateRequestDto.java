package com.currency.teamflow.domain.boardlist.dto;

import lombok.Getter;

@Getter
public class BoardListUpdateRequestDto {

	private final String boardListTitle;//리스트 제목

	private final Long array;//리스트 순서

	public BoardListUpdateRequestDto(String boardListTitle, Long array) {
		this.boardListTitle = boardListTitle;
		this.array = array;
	}

}

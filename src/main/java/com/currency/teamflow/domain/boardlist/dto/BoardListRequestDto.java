package com.currency.teamflow.domain.boardlist.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class BoardListRequestDto {

	@NotNull
	private final Long boardId;//워크스페이스 id(외래키)

	@NotNull
	private final String ListTitle;//리스트 제목

	public BoardListRequestDto(Long boardId, String listTitle) {
		this.boardId = boardId;
		ListTitle = listTitle;
	}
}

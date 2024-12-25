package com.currency.teamflow.domain.boardlist.dto;


import com.currency.teamflow.domain.boardlist.entity.BoardList;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class BoardListResponseDto {

	private final Long id;//보드 id

	private final String listTitle;//리스트 제목

	private final Long array;//리스트 순서

	private final LocalDateTime createdAt;//생성일

	private final LocalDateTime modifiedAt;//수정일

	public BoardListResponseDto(Long id, String listTitle, Long array, LocalDateTime createdAt,
		LocalDateTime modifiedAt) {
		this.id = id;
		this.listTitle = listTitle;
		this.array = array;
		this.createdAt = createdAt;
		this.modifiedAt = modifiedAt;
	}

	public static BoardListResponseDto toDto(BoardList boardList) {
		return new BoardListResponseDto(
			boardList.getId(),
			boardList.getListTitle(),
			boardList.getArray(),
			boardList.getCreatedAt(),
			boardList.getModifiedAt()
		);
	}
}

package com.currency.teamflow.domain.board.dto;

import com.currency.teamflow.domain.board.entity.Board;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class BoardResponseDto {

	private final Long id;//보드 id

//	private final Long workspaceId;//워크스페이스 id

	private final String boardTitle;//보드 제목

	private final String boardBackgroundColor;//보드 배경색

	private final String imageUrl;//보드 이미지

	private final LocalDateTime createdAt;//생성일

	private final LocalDateTime modifiedAt;//수정일

	public BoardResponseDto(Long id, String boardTitle, String boardBackgroundColor,
		String imageUrl, LocalDateTime createdAt, LocalDateTime modifiedAt) {
		this.id = id;
		this.boardTitle = boardTitle;
		this.boardBackgroundColor = boardBackgroundColor;
		this.imageUrl = imageUrl;
		this.createdAt = createdAt;
		this.modifiedAt = modifiedAt;
	}

	public static BoardResponseDto toDto(Board board, String imageUrl) {
		return new BoardResponseDto(
			board.getId(),
			board.getBoardTitle(),
			board.getBoardBackgroundColor(),
			imageUrl,
			board.getCreatedAt(),
			board.getModifiedAt()
		);
	}
}

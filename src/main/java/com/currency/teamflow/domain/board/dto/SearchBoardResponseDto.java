package com.currency.teamflow.domain.board.dto;

import com.currency.teamflow.domain.board.entity.Board;
import com.currency.teamflow.domain.boardlist.dto.BoardListResponseDto;
import com.currency.teamflow.domain.card.dto.CardResponseDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
public class SearchBoardResponseDto {

//	private final Long workspaceId;//워크스페이스 id

	private final Long id;//보드 id

	private final String boardTitle;//보드 제목

	private final String boardBackgroundColor;//보드 배경색

	private final String imageUrl;//보드 이미지

	private final LocalDateTime createdAt;//생성일

	private final LocalDateTime modifiedAt;//수정일

	private final List<BoardListResponseDto> boardListInfo; //보드 리스트 정보

	private final List<CardResponseDto> cardInfo; //카드 정보

	/**
	 * 보드 단건 조회
	 * -flatMap 사용
	 */
	public SearchBoardResponseDto(Long id, String boardTitle,
		String boardBackgroundColor, String imageUrl, LocalDateTime createdAt,
		LocalDateTime modifiedAt, List<BoardListResponseDto> boardListInfo, List<CardResponseDto> cardInfo) {
		this.id = id;
		this.boardTitle = boardTitle;
		this.boardBackgroundColor = boardBackgroundColor;
		this.imageUrl = imageUrl;
		this.createdAt = createdAt;
		this.modifiedAt = modifiedAt;
		this.boardListInfo = boardListInfo;
		this.cardInfo = cardInfo;
	}

	/**
	 * 보드 단건 조회
	 * -flatMap 사용
	 */
	public static SearchBoardResponseDto toDto(Board board, List<BoardListResponseDto> boardListResponseDto, List<CardResponseDto> cardResponseDto) {
		return new SearchBoardResponseDto(
			board.getId(),
			board.getBoardTitle(),
			board.getBoardBackgroundColor(),
			board.getImageUrl(),
			board.getCreatedAt(),
			board.getModifiedAt(),
			boardListResponseDto,
			cardResponseDto
		);
	}
}

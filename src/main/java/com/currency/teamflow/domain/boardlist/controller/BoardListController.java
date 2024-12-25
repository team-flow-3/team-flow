package com.currency.teamflow.domain.boardlist.controller;

import com.currency.teamflow.domain.board.dto.BoardRequestDto;
import com.currency.teamflow.domain.board.dto.BoardResponseDto;
import com.currency.teamflow.domain.boardlist.dto.BoardListRequestDto;
import com.currency.teamflow.domain.boardlist.dto.BoardListResponseDto;
import com.currency.teamflow.domain.boardlist.service.BoardListService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/boardList")
public class BoardListController {

	private final BoardListService boardListService;

	public BoardListController(BoardListService boardListService) {
		this.boardListService = boardListService;
	}

	/**
	 * 보드 리스트 생성 API
	 * - 보드 id 포함
	 */
	@PostMapping
	public ResponseEntity<BoardListResponseDto> createBoardList(@Valid @RequestBody BoardListRequestDto boardListRequestDto) {
		BoardListResponseDto boardListResponseDto = boardListService.createBoardList(
			boardListRequestDto.getBoardId(),
			boardListRequestDto.getListTitle()
		);

		return new ResponseEntity<>(boardListResponseDto, HttpStatus.CREATED);
	};
}

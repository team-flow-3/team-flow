package com.currency.teamflow.domain.board.controller;

import com.currency.teamflow.domain.board.dto.BoardRequestDto;
import com.currency.teamflow.domain.board.dto.BoardResponseDto;
import com.currency.teamflow.domain.board.service.BoardService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/boards")
public class BoardController {

	private final BoardService boardService;

	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}

	/**
	 * 보드 생성 API
	 * - 워크스페이스 id 포함
	 */
	@PostMapping
	public ResponseEntity<BoardResponseDto> createBoard(@Valid @ModelAttribute BoardRequestDto boardRequestDto,
		@RequestPart(required = false) MultipartFile image) {
		BoardResponseDto boardResponseDto = boardService.createBoard(
			boardRequestDto.getWorkspaceId(),
			boardRequestDto.getBoardTitle(),
			boardRequestDto.getBoardBackgroundColor(),
			image
		);

		return new ResponseEntity<>(boardResponseDto, HttpStatus.CREATED);
	};
}

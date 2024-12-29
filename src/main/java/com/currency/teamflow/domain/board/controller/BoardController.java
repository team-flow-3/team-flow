package com.currency.teamflow.domain.board.controller;

import com.currency.teamflow.domain.board.dto.BoardRequestDto;
import com.currency.teamflow.domain.board.dto.BoardResponseDto;
import com.currency.teamflow.domain.board.dto.SearchBoardResponseDto;
import com.currency.teamflow.domain.board.service.BoardService;
import com.currency.teamflow.global.annotation.CheckMemberRole;
import com.currency.teamflow.global.enums.Role;
import jakarta.validation.Valid;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
	 * - 워크스페이스 관리자, 보드 권한 받은 유저 허용
	 */
	@CheckMemberRole(requiredRoles = {Role.WORKSPACE_ADMIN, Role.BOARD_USER})
	@PostMapping
	public ResponseEntity<BoardResponseDto> createBoard(@Valid @ModelAttribute BoardRequestDto boardRequestDto,
		@RequestPart(required = false) MultipartFile image) throws IOException {

		BoardResponseDto boardResponseDto = boardService.createBoard(
			boardRequestDto,
			image
		);

		return new ResponseEntity<>(boardResponseDto, HttpStatus.CREATED);
	};

	/**
	 * 보드 단건 조회 API
	 * - 워크스페이스 id 포함
	 * - 워크스페이스 초대받은 모든 유저 허용
	 */
	@CheckMemberRole(requiredRoles = {Role.WORKSPACE_ADMIN, Role.BOARD_USER, Role.READ_ONLY})
	@GetMapping("/{boardId}")
	public ResponseEntity<SearchBoardResponseDto> selectBoard(@PathVariable Long boardId){

		SearchBoardResponseDto searchBoardResponseDto = boardService.selectBoard(boardId);

		return new ResponseEntity<>(searchBoardResponseDto, HttpStatus.OK);
	}
}

package com.currency.teamflow.domain.board.controller;


import com.currency.teamflow.domain.board.dto.BoardRequestDto;
import com.currency.teamflow.domain.board.dto.BoardResponseDto;
import com.currency.teamflow.domain.board.dto.SearchBoardResponseDto;
import com.currency.teamflow.domain.board.service.BoardService;
import com.currency.teamflow.domain.user.entity.WorkspaceUser;
import com.currency.teamflow.global.annotation.CheckMemberRole;
import com.currency.teamflow.global.enums.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
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
	public ResponseEntity<BoardResponseDto> createBoard(@Valid
		@ModelAttribute BoardRequestDto boardRequestDto,
		@RequestPart(required = false) MultipartFile image,
		HttpServletRequest httpServletRequest) throws IOException {

		HttpSession httpSession = httpServletRequest.getSession(false);
		WorkspaceUser workspaceUser = (WorkspaceUser) httpSession.getAttribute("workspaceUser");

		BoardResponseDto boardResponseDto = boardService.createBoard(
			workspaceUser,
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

	/**
	 * 보드 삭제 API
	 * - 워크스페이스 관리자, 보드 권한 받은 유저 허용
	 *  - 삭제시 모든 보드 리스트와 카드 데이터도 삭제
	 */
	@CheckMemberRole(requiredRoles = {Role.WORKSPACE_ADMIN, Role.BOARD_USER})
	@DeleteMapping("/{boardId}")
	public ResponseEntity<String> deleteBoard(@PathVariable Long boardId){
		boardService.deleteBoard(boardId);

		return new ResponseEntity<>("보드가 삭제되었습니다.", HttpStatus.OK);
	}

	/**
	 * 보드 수정 API
	 * - 워크스페이스 관리자, 보드 권한 받은 유저 허용
	 */
	@CheckMemberRole(requiredRoles = {Role.WORKSPACE_ADMIN, Role.BOARD_USER})
	@PatchMapping("/{boardId}")
	public ResponseEntity<BoardResponseDto> updateBoard(
		@PathVariable Long boardId,
		@ModelAttribute BoardRequestDto BoardRequestDto,
		@RequestPart(required = false) MultipartFile image ,
		HttpServletRequest httpServletRequest) throws IOException {

		//워크스페이스 유저 세션에서 가져오기
		HttpSession httpSession = httpServletRequest.getSession(false);
		WorkspaceUser workspaceUser = (WorkspaceUser) httpSession.getAttribute("workspaceUser");

		BoardResponseDto boardResponseDto = boardService.updateBoard(boardId, workspaceUser, image);

		return new ResponseEntity<>(boardResponseDto, HttpStatus.OK);
	}
}

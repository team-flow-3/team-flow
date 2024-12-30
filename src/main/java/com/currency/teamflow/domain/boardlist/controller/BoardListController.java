package com.currency.teamflow.domain.boardlist.controller;

import com.currency.teamflow.domain.boardlist.dto.BoardListRequestDto;
import com.currency.teamflow.domain.boardlist.dto.BoardListResponseDto;
import com.currency.teamflow.domain.boardlist.dto.BoardListUpdateRequestDto;
import com.currency.teamflow.domain.boardlist.service.BoardListService;
import com.currency.teamflow.domain.user.entity.WorkspaceUser;
import com.currency.teamflow.global.annotation.CheckMemberRole;
import com.currency.teamflow.global.enums.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	 * - 워크스페이스 관리자, 보드 권한 보유 유저 허용
	 */
	@CheckMemberRole(requiredRoles = {Role.WORKSPACE_ADMIN, Role.BOARD_USER})
	@PostMapping
	public ResponseEntity<BoardListResponseDto> createBoardList(
			@Valid @RequestBody BoardListRequestDto boardListRequestDto,
			HttpServletRequest httpServletRequest) {

		// 인증 정보 내의 유저 정보 가져오기
		HttpSession httpSession = httpServletRequest.getSession(false);
		WorkspaceUser workspaceUser = (WorkspaceUser) httpSession.getAttribute("workspaceUser");

		BoardListResponseDto boardListResponseDto = boardListService.createBoardList(
				boardListRequestDto,
				workspaceUser
		);

		return new ResponseEntity<>(boardListResponseDto, HttpStatus.CREATED);
	}

	/**
	 * 보드 리스트 단건 조회 API
	 * - 워크스페이스 초대된 모든 유저 가능
	 */
	@CheckMemberRole(requiredRoles = {Role.WORKSPACE_ADMIN, Role.BOARD_USER, Role.READ_ONLY})
	@GetMapping("/{boardListId}")
	public ResponseEntity<BoardListResponseDto> selectBoardList(@PathVariable Long boardListId) {
		BoardListResponseDto boardListResponseDto = boardListService.selectBoardList(boardListId);

		return new ResponseEntity<>(boardListResponseDto, HttpStatus.OK);
	}

	/**
	 * 보드 리스트 삭제 API
	 * - 워크스페이스 관리자, 보드 권한 보유 유저 허용
	 * - 삭제시 모든 카드와 데이터도 삭제
	 */
	@CheckMemberRole(requiredRoles = {Role.WORKSPACE_ADMIN, Role.BOARD_USER})
	@DeleteMapping("/{boardListId}")
	public ResponseEntity<String> deleteBoardList(@PathVariable Long boardListId){
		boardListService.deleteBoardList(boardListId);

		return new ResponseEntity<>("보드 리스트가 삭제되었습니다.", HttpStatus.OK);
	}

	/**
	 * 보드 리스트 수정 API
	 * - 워크스페이스 관리자, 보드 권한 보유 유저 허용
	 * - 보드 내에서의 순서를 변경할 수 있다
	 */
	@CheckMemberRole(requiredRoles = {Role.WORKSPACE_ADMIN , Role.BOARD_USER})
	@PatchMapping("/{boardListId}")
	public ResponseEntity<BoardListResponseDto> updateBoardList(
		@PathVariable Long boardListId,
		@RequestBody BoardListUpdateRequestDto boardListUpdateRequestDto,
		HttpServletRequest httpServletRequest){

		HttpSession httpSession = httpServletRequest.getSession(false);
		WorkspaceUser workspaceUser = (WorkspaceUser) httpSession.getAttribute("workspaceUser");

		BoardListResponseDto boardListResponseDto = boardListService.updateBoardList(
			boardListId,
			workspaceUser,
			boardListUpdateRequestDto
		);

		return new ResponseEntity<>(boardListResponseDto, HttpStatus.OK);
	}
	
}

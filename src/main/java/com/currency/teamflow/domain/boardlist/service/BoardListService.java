package com.currency.teamflow.domain.boardlist.service;

import com.currency.teamflow.domain.board.entity.Board;
import com.currency.teamflow.domain.board.repository.BoardRepository;
import com.currency.teamflow.domain.boardlist.dto.BoardListRequestDto;
import com.currency.teamflow.domain.boardlist.dto.BoardListResponseDto;
import com.currency.teamflow.domain.boardlist.entity.BoardList;
import com.currency.teamflow.domain.boardlist.repository.BoardListRepository;
import com.currency.teamflow.domain.user.entity.WorkspaceUser;
import com.currency.teamflow.domain.workspace.repository.WorkspaceRepository;
import com.currency.teamflow.domain.workspaceuser.repository.WorkspaceUserRepository;
import com.currency.teamflow.global.error.errorcode.ErrorCode;
import com.currency.teamflow.global.error.exception.CustomException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BoardListService {

	private final BoardListRepository boardListRepository;
	private final BoardRepository boardRepository;
	private final WorkspaceUserRepository workspaceUserRepository;
	private final WorkspaceRepository workspaceRepository;

	public BoardListService(BoardListRepository boardListRepository,
		BoardRepository boardRepository, WorkspaceUserRepository workspaceUserRepository,
		WorkspaceRepository workspaceRepository) {
		this.boardListRepository = boardListRepository;
		this.boardRepository = boardRepository;
		this.workspaceUserRepository = workspaceUserRepository;
		this.workspaceRepository = workspaceRepository;
	}

	/**
	 * 보드 리스트 생성 서비스 메서드
	 * - 워크스페이스 관리자, 보드 권한 가진 유저 허용
	 * TODO : 로그 정리
	 */
	@Transactional
	public BoardListResponseDto createBoardList(BoardListRequestDto boardListRequestDto,
		WorkspaceUser workspaceUser) {

		//보드 정보 가져오기
		Board board = boardRepository.findBoardIdByWorkspaceId(workspaceUser.getWorkspace().getId(), boardListRequestDto.getBoardId())
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_BOARD));

//		//보드 정보 가져오기
//		Board board = boardRepository.findByIdOrElseThrow(boardListRequestDto.getBoardId());
		log.info("boardId : {}" , board.getId());

		//리스트 크기, array 최대값
		int arrayNumber = board.getBoardLists().size();
		//리스트 생성
		BoardList boardList = new BoardList(board, boardListRequestDto.getBoardListTitle());
		boardList.addArrayNumber(arrayNumber + 1);
		//리스트 저장
		BoardList savedBoardList = boardListRepository.save(boardList);

		return BoardListResponseDto.toDto(savedBoardList);
	}

	/**
	 * 보드 리스트 단건 조회 서비스 메서드
	 * - 워크스페이스 초대된 모든 유저 가능
	 */
	public BoardListResponseDto selectBoardList(Long boardListId) {
		//입력받은 보드 리스트가 있는지 확인
		BoardList boardList  = boardListRepository.findByIdOrElseThrow(boardListId);

		return BoardListResponseDto.toDto(boardList);
	}

	/**
	 * 보드 리스트 삭제 서비스 메서드
	 * - 워크스페이스 관리자, 보드 권한 보유 유저 허용
	 * - 삭제시 모든 카드와 데이터도 삭제
	 */
	public void deleteBoardList(Long boardListId) {
		//보드 리스트 정보 가져오기
		BoardList boardList = boardListRepository.findByIdOrElseThrow(boardListId);
		boardListRepository.delete(boardList);
	}
}

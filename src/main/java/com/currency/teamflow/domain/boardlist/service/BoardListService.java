package com.currency.teamflow.domain.boardlist.service;

import com.currency.teamflow.domain.board.entity.Board;
import com.currency.teamflow.domain.board.repository.BoardRepository;
import com.currency.teamflow.domain.boardlist.dto.BoardListResponseDto;
import com.currency.teamflow.domain.boardlist.entity.BoardList;
import com.currency.teamflow.domain.boardlist.repository.BoardListRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BoardListService {

	private final BoardListRepository boardListRepository;
	private final BoardRepository boardRepository;

	public BoardListService(BoardListRepository boardListRepository,
		BoardRepository boardRepository) {
		this.boardListRepository = boardListRepository;
		this.boardRepository = boardRepository;
	}

	/**
	 * 보드 리스트 생성 API
	 * - 보드 id 포함
	 */
	@Transactional
	public BoardListResponseDto createBoardList(Long BoardId, String listTitle) {
		//보드 id 가져오기
		Board board = boardRepository.findByIdOrElseThrow(BoardId);
		//보드의 리스트 array 최대값 조회
		Long arrayNumber = boardRepository.findArrayByBoardOrElseThrow(board);

		//리스트 생성
		BoardList boardList = new BoardList(board, listTitle);
		boardList.addArrayNumber(arrayNumber + 1);
		//리스트 저장
		BoardList savedBoardList = boardListRepository.save(boardList);

		return BoardListResponseDto.toDto(savedBoardList);
	}

}

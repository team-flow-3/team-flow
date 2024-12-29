package com.currency.teamflow.domain.board.service;

import com.currency.teamflow.domain.attachment.service.AttachmentService;
import com.currency.teamflow.domain.board.dto.BoardRequestDto;
import com.currency.teamflow.domain.board.dto.BoardResponseDto;
import com.currency.teamflow.domain.board.dto.SearchBoardResponseDto;
import com.currency.teamflow.domain.board.entity.Board;
import com.currency.teamflow.domain.board.repository.BoardRepository;
import com.currency.teamflow.domain.boardlist.dto.BoardListResponseDto;
import com.currency.teamflow.domain.boardlist.repository.BoardListRepository;
import com.currency.teamflow.domain.card.dto.CardResponseDto;
import com.currency.teamflow.domain.card.repository.CardRepository;
import com.currency.teamflow.domain.card.service.CardService;
import com.currency.teamflow.domain.workspace.entity.Workspace;
import com.currency.teamflow.domain.workspace.repository.WorkspaceRepository;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class BoardService {

	private final BoardRepository boardRepository;
	private final BoardListRepository boardListRepository;
	private final WorkspaceRepository workspaceRepository;
	private final AttachmentService attachmentService;
	private final CardRepository cardRepository;
	private final CardService cardService;

	public BoardService(BoardRepository boardRepository, BoardListRepository boardListRepository, WorkspaceRepository workspaceRepository,
		AttachmentService attachmentService, CardRepository cardRepository, CardService cardService) {
		this.boardRepository = boardRepository;
		this.boardListRepository = boardListRepository;
		this.workspaceRepository = workspaceRepository;
		this.attachmentService = attachmentService;
		this.cardRepository = cardRepository;
		this.cardService = cardService;
	}

	/**
	 * 보드 생성 API
	 * - 워크스페이스 id 포함
	 * - 이미지는 하나만 받음
	 */
	@Transactional
	public BoardResponseDto createBoard(BoardRequestDto boardRequestDto, MultipartFile image)
		throws IOException {

		//워크스페이스 id 가져오기
		Workspace workspace = workspaceRepository.findByIdOrElseThrow(boardRequestDto.getWorkspaceId());

		//이미지가 있을 경우
		String imageUrl = null;
		if(image != null && !image.isEmpty()){
			String attachmentResponseDto = attachmentService.createAttachmentForBoard(image);

			//url 가져오기
			if(!attachmentResponseDto.isEmpty()){
				imageUrl = attachmentResponseDto;
			}
		}

		//보드 생성
		Board board = new Board(workspace, boardRequestDto.getBoardTitle(), boardRequestDto.getBoardBackgroundColor(), imageUrl);

		//로그 확인
		log.info("workspaceId: {}", workspace);
		log.info("boardTitle: {}", boardRequestDto.getBoardTitle());
		log.info("boardBackgroundColor: {}", boardRequestDto.getBoardBackgroundColor());
		log.info("ImageUrl : {}", imageUrl);

		//보드 저장
		Board savedBoard = boardRepository.save(board);

		return BoardResponseDto.toDto(savedBoard, imageUrl);
	}

	/**
	 * 보드 단건 조회 API
	 * - 워크스페이스 id 포함
	 * - 워크스페이스 초대받은 모든 유저 허용
	 */
	public SearchBoardResponseDto selectBoard(Long boardId) {
		//보드 정보 가져오기
		Board board = boardRepository.findByIdOrElseThrow(boardId);

		//해당 보드의 보드 리스트 전부 가져오기
		List<BoardListResponseDto> boardListResponseDto = boardListRepository.findAllBoardListByBoardId(boardId);

		//해당 보드 리스트의 카드 전부 가져오기
		List<CardResponseDto> cardResponseDto = boardListResponseDto
			.stream()
			.flatMap(boardList -> cardService.getCards(boardList.getId())
				.stream()).toList();

		return SearchBoardResponseDto.toDto(board, boardListResponseDto, cardResponseDto);
	}

	/**
	 * 보드 단건 조회 서비스 메서드
	 * - 워크스페이스 초대받은 모든 유저 허용
	 * - 삭제시 모든 보드 리스트와 카드 데이터도 삭제
	 */
	public void deleteBoard(Long boardId) {
		//보드 정보 가져오기
		Board board = boardRepository.findByIdOrElseThrow(boardId);
		attachmentService.deleteAttachmentForBoard(board.getImageUrl());
		boardRepository.delete(board);
	}
}

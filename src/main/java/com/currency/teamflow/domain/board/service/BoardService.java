package com.currency.teamflow.domain.board.service;

import com.currency.teamflow.domain.board.dto.BoardResponseDto;
import com.currency.teamflow.domain.board.entity.Board;
import com.currency.teamflow.domain.board.repository.BoardRepository;
import com.currency.teamflow.domain.workspace.entity.Workspace;
import com.currency.teamflow.domain.workspace.repository.WorkspaceRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class BoardService {

	private final BoardRepository boardRepository;
	private final WorkspaceRepository workspaceRepository;

	public BoardService(BoardRepository boardRepository, WorkspaceRepository workspaceRepository) {
		this.boardRepository = boardRepository;
		this.workspaceRepository = workspaceRepository;
	}

	/**
	 * 보드 생성 API
	 *
	 */
	@Transactional
	public BoardResponseDto createBoard(Long WorkspaceId, String boardTitle, String boardBackgroundColor, MultipartFile image) {
		//워크스페이스 id 가져오기
		Workspace workspace = workspaceRepository.findByIdOrElseThrow(WorkspaceId);

		//이미지 파일명
		String imageUrl = (image != null && !image.isEmpty()) ? image.getOriginalFilename() : "temp.jpg";

		//보드 생성
		Board board = new Board(workspace, boardTitle, boardBackgroundColor, imageUrl);

		//로그 확인
		log.info("workspaceId: {}", workspace);
		log.info("boardTitle: {}", boardTitle);
		log.info("boardBackgroundColor: {}", boardBackgroundColor);
		log.info("ImageUrl : {}", imageUrl);

		//보드 저장
		Board savedBoard = boardRepository.save(board);

		return BoardResponseDto.toDto(savedBoard, imageUrl);
	}

}

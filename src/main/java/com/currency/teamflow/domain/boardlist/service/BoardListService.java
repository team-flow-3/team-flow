package com.currency.teamflow.domain.boardlist.service;

import com.currency.teamflow.domain.board.entity.Board;
import com.currency.teamflow.domain.board.repository.BoardRepository;
import com.currency.teamflow.domain.boardlist.dto.BoardListRequestDto;
import com.currency.teamflow.domain.boardlist.dto.BoardListResponseDto;
import com.currency.teamflow.domain.boardlist.dto.BoardListUpdateRequestDto;
import com.currency.teamflow.domain.boardlist.entity.BoardList;
import com.currency.teamflow.domain.boardlist.repository.BoardListRepository;
import com.currency.teamflow.domain.user.entity.WorkspaceUser;
import com.currency.teamflow.domain.workspace.repository.WorkspaceRepository;
import com.currency.teamflow.domain.workspaceuser.repository.WorkspaceUserRepository;
import com.currency.teamflow.global.error.errorcode.ErrorCode;
import com.currency.teamflow.global.error.exception.CustomException;
import jakarta.persistence.Index;
import jakarta.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
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
		Board board = boardRepository.findBoardIdByWorkspaceIdAndBoardId(workspaceUser.getWorkspace().getId(), boardListRequestDto.getBoardId())
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_BOARD));
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
	@Transactional
	public void deleteBoardList(Long boardListId) {
		//보드 리스트 정보 가져오기
		BoardList boardList = boardListRepository.findByIdOrElseThrow(boardListId);
		boardListRepository.delete(boardList);
	}

	/**
	 * 보드 리스트 수정 API
	 * - 워크스페이스 관리자, 보드 권한 보유 유저 허용
	 * - 보드 내에서의 순서를 변경할 수 있다
	 */
	@Transactional
	public BoardListResponseDto updateBoardList(Long boardListId, WorkspaceUser workspaceUser,
		BoardListUpdateRequestDto boardListUpdateRequestDto) {
		//보드 리스트 정보 가져오기 (순서 변경할 보드 리스트 id 가져오기)
		BoardList boardList = boardListRepository.findByIdOrElseThrow(boardListId);
		log.info("boardListInformation: {}", boardList.getId());

		//순서를 변경하고싶은 보드 리스트 array
		Long changeBordListArray = boardList.getArray();
		log.info("changeBordListArray: {}", changeBordListArray);

		//이동할 순서 array - 요청받은 array
		Long updateBoardListArray = boardListUpdateRequestDto.getArray();
		log.info("updateBoardListArray: {}", updateBoardListArray);

		//array가 null값이 들어올 경우
		if(updateBoardListArray == null){
			updateBoardListArray = boardList.getArray();
		}

		//요청받은 boardListTitle 가져오기
		String boardListTitle = boardListUpdateRequestDto.getBoardListTitle();
		log.info("boardListTitle: {}", boardListTitle);

		//만약 순서와 타이틀 내용이 동일한 경우
		if (changeBordListArray.equals(updateBoardListArray) && boardListTitle.equals(boardList.getBoardListTitle())) {
			//바로 반환
			return new BoardListResponseDto(boardList);
		}

		//저장되어있는 모든 보드 리스트 array 정렬해서 가져오기
		List<BoardList> allBoardListArray = boardListRepository.findAllByBoardOrderByArrayAsc(boardList.getBoard().getId());
		log.info("allBoardListArray size : " , allBoardListArray);

		// 순서 변경
		//만약 순서를 변경하고싶은 보드 리스트 array가 이동할 array 보다 작을 경우
		//ex) 3 -> 5
		if (changeBordListArray < updateBoardListArray) {
			//해당 보드리스트 뒤에있는 보드 리스트들 array 순서가 앞으로 당겨져야함
			//ex) 4, 5 ->  3, 4로 변경
			Long finalUpdateBoardListArray = updateBoardListArray;
			allBoardListArray.stream()
				.filter(list -> list.getArray() > changeBordListArray && list.getArray() <= finalUpdateBoardListArray.longValue())
				.forEach(list -> list.updateArray(list.getArray() -1));
		}

		//만약 순서를 변경하고싶은 보드 리스트 array가 이동할 array 보다 클 경우
		//ex) 5 -> 3
		if (changeBordListArray > updateBoardListArray) {
			//해당 보드리스트 앞에있는 보드 리스트들 array 순서가 순서가 뒤로 밀려야함
			//ex) 3, 4 -> 4, 5로 변경
			Long finalUpdateBoardListArray = updateBoardListArray;
			allBoardListArray.stream()
				.filter(list -> list.getArray() >= finalUpdateBoardListArray.longValue() && list.getArray() < changeBordListArray)
				.forEach(list -> list.updateArray(list.getArray() + 1));
		}

		//변경된 순서 & 제목 업데이트 업데이트 저장
		boardList.updateArray(updateBoardListArray);
		boardList.updateBoardListTitle(boardListTitle);

		//변경된 사항 저장
		allBoardListArray.forEach(boardListRepository::save);
		return new BoardListResponseDto(boardList);
	}

}

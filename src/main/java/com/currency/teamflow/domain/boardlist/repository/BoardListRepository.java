package com.currency.teamflow.domain.boardlist.repository;

import com.currency.teamflow.domain.boardlist.dto.BoardListResponseDto;
import com.currency.teamflow.domain.boardlist.entity.BoardList;
import com.currency.teamflow.global.error.errorcode.ErrorCode;
import com.currency.teamflow.global.error.exception.CustomException;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardListRepository extends JpaRepository<BoardList, Long> {

	default BoardList findByIdOrElseThrow(Long BoardListId) {
		return findById(BoardListId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_BOARDLIST));
	}

	@Query("SELECT new com.currency.teamflow.domain.boardlist.dto.BoardListResponseDto(bl.id, bl.boardListTitle, bl.array, bl.createdAt, bl.modifiedAt) "
		+ "FROM BoardList bl "
		+ "WHERE bl.board.id = :boardId")
	List<BoardListResponseDto> findAllBoardListByBoardId(Long boardId);

	@Query("SELECT bl " +
		"FROM BoardList bl " +
		"WHERE bl.board.id = :boardId " +
		"ORDER BY bl.array ASC")
	List<BoardList> findAllByBoardOrderByArrayAsc(Long boardId);

}

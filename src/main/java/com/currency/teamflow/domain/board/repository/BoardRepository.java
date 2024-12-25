package com.currency.teamflow.domain.board.repository;


import com.currency.teamflow.domain.board.entity.Board;
import com.currency.teamflow.global.error.errorcode.ErrorCode;
import com.currency.teamflow.global.error.exception.CustomException;
import java.util.Optional;
import java.util.OptionalLong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
	default Board findByIdOrElseThrow(Long boardId){
		return findById(boardId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
	}

	@Query("SELECT COALESCE(MAX(bl.array), 0)"
		+ "FROM BoardList bl "
		+ "WHERE bl.board = :board")
	Long findArrayByBoardOrElseThrow(Board board);


}

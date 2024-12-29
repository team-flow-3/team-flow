package com.currency.teamflow.domain.board.repository;

import com.currency.teamflow.domain.board.entity.Board;
import com.currency.teamflow.global.error.errorcode.ErrorCode;
import com.currency.teamflow.global.error.exception.CustomException;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
	default Board findByIdOrElseThrow(Long boardId){
		return findById(boardId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_BOARD));
	}

	/**
	 * workspaceId와 boardId로 Board 조회
	 */
	@Query("SELECT b "
		+ "FROM Board b "
		+ "WHERE b.workspace.id = :workspaceId AND b.id = :boardId")
	Optional<Board> findBoardIdByWorkspaceId(Long workspaceId, Long boardId);
}

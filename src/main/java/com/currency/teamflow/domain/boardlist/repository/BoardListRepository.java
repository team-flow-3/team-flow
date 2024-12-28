package com.currency.teamflow.domain.boardlist.repository;

import com.currency.teamflow.domain.boardlist.entity.BoardList;
import com.currency.teamflow.global.error.errorcode.ErrorCode;
import com.currency.teamflow.global.error.exception.CustomException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardListRepository extends JpaRepository<BoardList, Long> {
	default BoardList findByIdOrElseThrow(Long BoardListId) {
		return findById(BoardListId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_BOARDLIST));
	}
}

package com.currency.teamflow.domain.card.repository;

import com.currency.teamflow.domain.card.entity.Card;
import com.currency.teamflow.global.error.errorcode.ErrorCode;
import com.currency.teamflow.global.error.exception.CustomException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {

    default Card findByIdOrElseThrow(Long cardId) {
        return findById(cardId).orElseThrow( () -> new CustomException(ErrorCode.NOT_FOUND));
    }

    List<Card> findAllByBoardListId(Long boardListId);
}

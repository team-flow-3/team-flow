package com.currency.teamflow.domain.card.repository;

import com.currency.teamflow.domain.card.entity.Card;
import com.currency.teamflow.global.error.errorcode.ErrorCode;
import com.currency.teamflow.global.error.exception.CustomException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryDefinition(domainClass = Card.class, idClass = Long.class)
public interface CardRepository extends JpaRepository<Card, Long>, CardRepositoryQuery {

    default Card findByIdOrElseThrow(Long cardId) {
        return findById(cardId).orElseThrow( () -> new CustomException(ErrorCode.NOT_FOUND));
    }

    List<Card> findAllByBoardListId(Long boardListId);
}

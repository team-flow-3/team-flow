package com.currency.teamflow.domain.card.repository;

import com.currency.teamflow.domain.card.entity.Card;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CardRepositoryQuery {

    List<Card> findAllSearchByConditions(Pageable pageable,
                                         Long boardId,
                                         String cardTitle,
                                         String cardExplanation,
                                         String endAt,
                                         String cardManagerName);
}

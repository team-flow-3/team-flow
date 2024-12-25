package com.currency.teamflow.domain.card.repository;

import com.currency.teamflow.domain.card.entity.CardManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardManagerRepository extends JpaRepository<CardManager, Long> {
    void deleteAllByCardCardId(Long cardId);
}

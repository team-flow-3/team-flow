package com.currency.teamflow.domain.card.repository;

import com.currency.teamflow.domain.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
    
}

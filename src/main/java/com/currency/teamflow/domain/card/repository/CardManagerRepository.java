package com.currency.teamflow.domain.card.repository;

import com.currency.teamflow.domain.card.entity.CardManager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardManagerRepository extends JpaRepository<CardManager, Long> {
}

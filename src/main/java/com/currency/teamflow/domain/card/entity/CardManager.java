package com.currency.teamflow.domain.card.entity;

import com.currency.teamflow.domain.user.entity.User;
import com.currency.teamflow.global.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "card_manager")
public class CardManager extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cardManagerId;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public CardManager() {

    }
}

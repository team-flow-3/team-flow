package com.currency.teamflow.domain.card.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CardUpdateRequestDto {

    private final String cardTitle;

    private final String cardExplanation;

    private final LocalDateTime endAt;

    private final List<Long> userIds;

    public CardUpdateRequestDto(String cardTitle, String cardExplanation, LocalDateTime endAt, List<Long> userIds) {
        this.cardTitle = cardTitle;
        this.cardExplanation = cardExplanation;
        this.endAt = endAt;
        this.userIds = userIds;
    }
}

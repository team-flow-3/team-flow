package com.currency.teamflow.domain.card.dto;

import lombok.Getter;

@Getter
public class CardSearchRequestDto {

    private final Long boardId;

    private final String cardTitle;

    private final String cardExplanation;

    private final String endAt;

    private final String cardManagerName;

    public CardSearchRequestDto(Long boardId, String cardTitle, String cardExplanation, String endAt, String cardManagerName) {
        this.boardId = boardId;
        this.cardTitle = cardTitle;
        this.cardExplanation = cardExplanation;
        this.endAt = endAt;
        this.cardManagerName = cardManagerName;
    }
}

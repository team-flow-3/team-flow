package com.currency.teamflow.domain.card.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class CardRequestDto {

    @NotNull
    private final Long boardListId;

    @NotNull
    private final String cardTitle;

    @NotNull
    private final String cardExplanation;

    private final LocalDate endAt;

    private final List<Long> userIds;

    public CardRequestDto(Long boardListId, String cardTitle, String cardExplanation, LocalDate endAt, List<Long> userIds) {
        this.boardListId = boardListId;
        this.cardTitle = cardTitle;
        this.cardExplanation = cardExplanation;
        this.endAt = endAt;
        this.userIds = userIds;
    }
}

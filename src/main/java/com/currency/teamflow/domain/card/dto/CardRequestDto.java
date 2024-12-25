package com.currency.teamflow.domain.card.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CardRequestDto {

    @NotNull
    private final Long listId;

    @NotNull
    private final String cardTitle;

    @NotNull
    private final String cardExplanation;

    private final LocalDateTime endAt;

    private final List<Long> userIds;

    public CardRequestDto(Long listId, String cardTitle, String cardExplanation, LocalDateTime endAt, List<Long> userIds) {
        this.listId = listId;
        this.cardTitle = cardTitle;
        this.cardExplanation = cardExplanation;
        this.endAt = endAt;
        this.userIds = userIds;
    }
}

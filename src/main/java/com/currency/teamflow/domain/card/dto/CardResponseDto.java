package com.currency.teamflow.domain.card.dto;

import com.currency.teamflow.domain.card.entity.Card;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
public class CardResponseDto {

    private final Long cardId;

    private final String cardTitle;

    private final String cardExplanation;

    private final LocalDate endAt;

    private final List<Map<Long, String>> userList;

    private final LocalDateTime createdAt;

    private final LocalDateTime modifiedAt;

    public CardResponseDto(Long cardId,
                           String cardTitle,
                           String cardExplanation,
                           LocalDate endAt,
                           List<Map<Long, String>> userList,
                           LocalDateTime createdAt,
                           LocalDateTime modifiedAt) {
        this.cardId = cardId;
        this.cardTitle = cardTitle;
        this.cardExplanation = cardExplanation;
        this.endAt = endAt;
        this.userList = userList;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static CardResponseDto toDto(Card card) {
        List<Map<Long, String>> userNicknameList = card.getCardManagers()
                .stream()
                .map(cardManager -> Map.of(cardManager.getUser().getId(), cardManager.getUser().getNickName()))
                .toList();

        return new CardResponseDto(
                card.getCardId(),
                card.getCardTitle(),
                card.getCardExplanation(),
                card.getEndAt(),
                userNicknameList,
                card.getCreatedAt(),
                card.getModifiedAt()
        );

    }
}

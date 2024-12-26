package com.currency.teamflow.domain.card.dto;

import com.currency.teamflow.domain.card.entity.Card;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CardResponseDto {

    private final Long cardId;

    private final String cardTitle;

    private final String cardExplanation;

    private final LocalDate endAt;

    private final List<String> userNicknameList;

    private final LocalDateTime createdAt;

    private final LocalDateTime modifiedAt;

    public CardResponseDto(Long cardId,
                           String cardTitle,
                           String cardExplanation,
                           LocalDate endAt,
                           List<String> userNicknameList,
                           LocalDateTime createdAt,
                           LocalDateTime modifiedAt) {
        this.cardId = cardId;
        this.cardTitle = cardTitle;
        this.cardExplanation = cardExplanation;
        this.endAt = endAt;
        this.userNicknameList = userNicknameList;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static CardResponseDto toDto(Card card) {
        List<String> userNicknameList = card.getCardManagers()
                .stream().map(user -> user.getUser().getNickName()).toList();

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

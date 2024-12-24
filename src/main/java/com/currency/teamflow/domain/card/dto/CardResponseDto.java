package com.currency.teamflow.domain.card.dto;

import com.currency.teamflow.domain.card.entity.Card;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CardResponseDto {

    private Long cardId;

    private String cardTitle;

    private String cardExplanation;

    private LocalDateTime endAt;

    private List<String> userNicknameList;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    public CardResponseDto(Long cardId,
                           String cardTitle,
                           String cardExplanation,
                           LocalDateTime endAt,
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

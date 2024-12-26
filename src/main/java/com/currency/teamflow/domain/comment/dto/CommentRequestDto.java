package com.currency.teamflow.domain.comment.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {

    private final Long cardId;

    private final String comment;

    public CommentRequestDto(Long cardId, String comment) {
        this.cardId = cardId;
        this.comment = comment;
    }
}

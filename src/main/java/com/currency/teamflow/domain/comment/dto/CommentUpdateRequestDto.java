package com.currency.teamflow.domain.comment.dto;

import lombok.Getter;

@Getter
public class CommentUpdateRequestDto {

    private final String comment;

    public CommentUpdateRequestDto(String comment) {
        this.comment = comment;
    }

}

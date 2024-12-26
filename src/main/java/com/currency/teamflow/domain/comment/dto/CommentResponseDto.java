package com.currency.teamflow.domain.comment.dto;
import com.currency.teamflow.domain.comment.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {

    private final Long commentId;

    private final Long cardId;

    private final String userNickName;

    private final String comment;

    private final LocalDateTime createdAt;

    private final LocalDateTime modifiedAt;


    public CommentResponseDto(Long commentId, Long cardId, String userNickName, String comment, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.commentId = commentId;
        this.cardId = cardId;
        this.userNickName = userNickName;
        this.comment = comment;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static CommentResponseDto toDto(Comment comment) {

        return new CommentResponseDto(
                comment.getCommentId(),
                comment.getCard().getCardId(),
                comment.getUser().getNickName(),
                comment.getComment(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }
}

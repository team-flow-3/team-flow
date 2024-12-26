package com.currency.teamflow.domain.comment.repository;

import com.currency.teamflow.domain.comment.entity.Comment;
import com.currency.teamflow.global.error.errorcode.ErrorCode;
import com.currency.teamflow.global.error.exception.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentsRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findAllByCardCardIdOrderByCreatedAtDesc(Long cardId, Pageable pageable);

    default Comment findByIdOrElseThrow(Long commentId) {
        return findById(commentId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
    }

}

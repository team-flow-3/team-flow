package com.currency.teamflow.domain.comment.repository;

import com.currency.teamflow.domain.comment.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentsRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findAllByCardCardIdOrderByCreatedAtDesc(Long cardId, Pageable pageable);
}

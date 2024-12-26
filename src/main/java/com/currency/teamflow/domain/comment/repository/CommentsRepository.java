package com.currency.teamflow.domain.comment.repository;

import com.currency.teamflow.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentsRepository extends JpaRepository<Comment, Long> {
}

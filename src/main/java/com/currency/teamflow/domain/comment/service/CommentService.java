package com.currency.teamflow.domain.comment.service;

import com.currency.teamflow.domain.card.entity.Card;
import com.currency.teamflow.domain.card.repository.CardRepository;
import com.currency.teamflow.domain.comment.dto.CommentRequestDto;
import com.currency.teamflow.domain.comment.dto.CommentResponseDto;
import com.currency.teamflow.domain.comment.entity.Comment;
import com.currency.teamflow.domain.comment.repository.CommentsRepository;
import com.currency.teamflow.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService {

    private final CommentsRepository commentsRepository;
    private final CardRepository cardRepository;

    public CommentService(CommentsRepository commentsRepository, CardRepository cardRepository) {
        this.commentsRepository = commentsRepository;
        this.cardRepository = cardRepository;
    }

    /**
     * 댓글 생성 서비스 메서드
     *
     * @param commentRequestDto 댓글 정보 dto
     * @return CommentResponseDto
     */
    @Transactional
    public CommentResponseDto createComment(User user, CommentRequestDto commentRequestDto) {

        Card card = cardRepository.findByIdOrElseThrow(commentRequestDto.getCardId());

        // 객체 생성
        Comment comment = new Comment(user, card, commentRequestDto.getComment());

        commentsRepository.save(comment);

        return CommentResponseDto.toDto(comment);
    }

    public List<CommentResponseDto> getComments(Pageable pageable, Long cardId) {

        Page<Comment> comments = commentsRepository.findAllByCardCardIdOrderByCreatedAtDesc(cardId, pageable);

        return comments.stream().map(CommentResponseDto::toDto).toList();
    }
}

package com.currency.teamflow.domain.comment.service;

import com.currency.teamflow.domain.card.entity.Card;
import com.currency.teamflow.domain.card.repository.CardRepository;
import com.currency.teamflow.domain.comment.dto.CommentRequestDto;
import com.currency.teamflow.domain.comment.dto.CommentResponseDto;
import com.currency.teamflow.domain.comment.dto.CommentUpdateRequestDto;
import com.currency.teamflow.domain.comment.entity.Comment;
import com.currency.teamflow.domain.comment.repository.CommentsRepository;
import com.currency.teamflow.domain.user.entity.User;
import com.currency.teamflow.global.alarm.AlarmService;
import com.currency.teamflow.global.error.errorcode.ErrorCode;
import com.currency.teamflow.global.error.exception.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService {

    private final CommentsRepository commentsRepository;
    private final CardRepository cardRepository;
    private final AlarmService alarmService;

    public CommentService(CommentsRepository commentsRepository, CardRepository cardRepository, AlarmService alarmService) {
        this.commentsRepository = commentsRepository;
        this.cardRepository = cardRepository;
        this.alarmService = alarmService;
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

        alarmService.AlarmMessage(card.getCardTitle() + " 카드에 댓글이 작성되었습니다."); // 댓글 작성 알람

        return CommentResponseDto.toDto(comment);
    }

    /**
     * 댓글 전체 조회 서비스 메서드
     *
     * @param pageable 페이징 객체
     * @param cardId 카드 식별자
     * @return List<CommentResponseDto>
     */
    public List<CommentResponseDto> getComments(Pageable pageable, Long cardId) {

        // 페이징을 통해 한 카드 내의 있는 댓글 가져오기 (생성일 기준 내림차순)
        Page<Comment> comments = commentsRepository.findAllByCardCardIdOrderByCreatedAtDesc(cardId, pageable);

        return comments.stream().map(CommentResponseDto::toDto).toList();
    }

    /**
     * 댓글 단건 수정 서비스 메서드
     *
     * @param commentId 댓글 식별자
     * @param commentUpdateRequestDto 수정할 댓글 내용
     * @return CommentResponseDto
     */
    @Transactional
    public CommentResponseDto updateComment(Long commentId, CommentUpdateRequestDto commentUpdateRequestDto, Long userId) {

        // 댓글 정보 가져오기
        Comment comment = commentsRepository.findByIdOrElseThrow(commentId);

        // 댓글 작성자 본인 확인
        if(!comment.getUser().getId().equals(userId)) {
            throw new CustomException(ErrorCode.NOT_YOUR_COMMENT);
        }

        // 댓글 수정
        comment.updateComment(commentUpdateRequestDto.getComment());

        // 댓글 저장
        commentsRepository.save(comment);

        return CommentResponseDto.toDto(comment);
    }

    /**
     * 댓글 단건 삭제 서비스 메서드
     *
     * @param commentId 댓글 식별자
     */
    @Transactional
    public void deleteComment(Long commentId, Long userId) {

        // 댓글 정보 가져오기
        Comment comment = commentsRepository.findByIdOrElseThrow(commentId);

        // 댓글 작성자 본인 확인
        if(!comment.getUser().getId().equals(userId)) {
            throw new CustomException(ErrorCode.NOT_YOUR_COMMENT);
        }

        // 댓글 삭제
        commentsRepository.deleteById(commentId);
    }
}

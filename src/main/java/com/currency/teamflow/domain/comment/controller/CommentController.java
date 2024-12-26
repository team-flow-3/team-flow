package com.currency.teamflow.domain.comment.controller;

import com.currency.teamflow.domain.comment.dto.CommentRequestDto;
import com.currency.teamflow.domain.comment.dto.CommentResponseDto;
import com.currency.teamflow.domain.comment.dto.CommentUpdateRequestDto;
import com.currency.teamflow.domain.comment.service.CommentService;
import com.currency.teamflow.domain.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * 댓글 생성 API
     *
     * @param commentRequestDto 저장할 댓글 정보 dto
     * @param request 요청 객체
     * @return ResponseEntity<CommentResponseDto>
     */
    @PostMapping("/comments")
    public ResponseEntity<CommentResponseDto> createComment(@Valid @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User loginedUser = (User) session.getAttribute("user");

        CommentResponseDto commentResponseDto = commentService.createComment(loginedUser, commentRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(commentResponseDto);
    }

    /**
     * 카드 내의 댓글 전체 조회 API
     *
     * @param pageable 페이징 객체
     * @param cardId 카드 식별자
     * @return ResponseEntity<List<CommentResponseDto>>
     */
    @GetMapping("/cards/{cardId}/comments")
    public ResponseEntity<List<CommentResponseDto>> getComments(@PageableDefault() Pageable pageable, @PathVariable Long cardId) {

        List<CommentResponseDto> commentResponseDtoList = commentService.getComments(pageable, cardId);

        return ResponseEntity.status(HttpStatus.OK).body(commentResponseDtoList);
    }

    /**
     * 댓글 단건 수정 API
     *
     * @param commentUpdateRequestDto 수정할 댓글 내용 dto
     * @param commentId 댓글 식별자
     * @return ResponseEntity<CommentResponseDto>
     */
    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(@Valid @RequestBody CommentUpdateRequestDto commentUpdateRequestDto,
                                                            @PathVariable Long commentId) {

        CommentResponseDto commentResponseDto = commentService.updateComment(commentId, commentUpdateRequestDto);

        return ResponseEntity.ok().body(commentResponseDto);
    }
}

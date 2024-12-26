package com.currency.teamflow.domain.comment.controller;

import com.currency.teamflow.domain.comment.dto.CommentRequestDto;
import com.currency.teamflow.domain.comment.dto.CommentResponseDto;
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

    @GetMapping("/cards/{cardId}/comments")
    public ResponseEntity<List<CommentResponseDto>> getComments(@PageableDefault() Pageable pageable, @PathVariable Long cardId) {

        List<CommentResponseDto> commentResponseDtoList = commentService.getComments(pageable, cardId);

        return ResponseEntity.status(HttpStatus.OK).body(commentResponseDtoList);
    }
}

package com.currency.teamflow.domain.comment.controller;

import com.currency.teamflow.domain.comment.dto.CommentRequestDto;
import com.currency.teamflow.domain.comment.dto.CommentResponseDto;
import com.currency.teamflow.domain.comment.dto.CommentUpdateRequestDto;
import com.currency.teamflow.domain.comment.service.CommentService;
import com.currency.teamflow.domain.user.entity.User;
import com.currency.teamflow.global.annotation.CheckMemberRole;
import com.currency.teamflow.global.config.auth.UserDetailsImpl;
import com.currency.teamflow.global.enums.Role;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
     * @param authentication 요청 객체
     * @return ResponseEntity<CommentResponseDto>
     */
    @CheckMemberRole(requiredRoles = {Role.WORKSPACE_ADMIN, Role.BOARD_USER})
    @PostMapping("/comments")
    public ResponseEntity<CommentResponseDto> createComment(@Valid @RequestBody CommentRequestDto commentRequestDto,
                                                            Authentication authentication) {
        // 인증 정보 내의 유저 정보 가져오기
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User loginedUser = userDetails.getUser();

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
    public ResponseEntity<List<CommentResponseDto>> getComments(@PageableDefault() Pageable pageable,
                                                                @PathVariable Long cardId) {

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
    @CheckMemberRole(requiredRoles = {Role.WORKSPACE_ADMIN, Role.BOARD_USER})
    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(@Valid @RequestBody CommentUpdateRequestDto commentUpdateRequestDto,
                                                            @PathVariable Long commentId,
                                                            Authentication authentication) {

        // 인증 정보 내의 유저 정보 가져오기
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User loginedUser = userDetails.getUser();

        CommentResponseDto commentResponseDto = commentService.updateComment(commentId, commentUpdateRequestDto, loginedUser.getId());

        return ResponseEntity.ok().body(commentResponseDto);
    }

    /**
     * 댓글 단건 삭제 API
     *
     * @param commentId 댓글 식별자
     * @return ResponseEntity<Void>
     */
    @CheckMemberRole(requiredRoles = {Role.WORKSPACE_ADMIN, Role.BOARD_USER})
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId, Authentication authentication) {

        // 인증 정보 내의 유저 정보 가져오기
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User loginedUser = userDetails.getUser();

        commentService.deleteComment(commentId, loginedUser.getId());

        return ResponseEntity.noContent().build();
    }
}

package com.currency.teamflow.domain.attachment.controller;

import com.currency.teamflow.domain.attachment.dto.AttachmentResponseDto;
import com.currency.teamflow.domain.attachment.service.AttachmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping
public class AttachmentController {

    private final AttachmentService attachmentService;

    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }


    /**
     * 첨부파일 추가 API
     *
     * @param cardId 카드 식별자
     * @param files 첨부파일
     * @return ResponseEntity<List<AttachmentResponseDto>>
     * @throws IOException
     */
    @PostMapping("/cards/{cardId}/attachments")
    public ResponseEntity<List<AttachmentResponseDto>> createAttachments(@PathVariable Long cardId,
                                                                       @Valid @RequestParam(required = false) List<MultipartFile> files) throws IOException {

        List<AttachmentResponseDto> responseDtoList = attachmentService.createAttachments(cardId, files);

        return ResponseEntity.ok(responseDtoList);
    }


    /**
     * 카드 내의 첨부파일 조회 API
     *
     * @param cardId 카드 식별자
     * @return ResponseEntity<List<AttachmentResponseDto>>
     */
    @GetMapping("/cards/{cardId}/attachments")
    public ResponseEntity<List<AttachmentResponseDto>> getAttachments(@PathVariable Long cardId){

        List<AttachmentResponseDto> responseDtoList = attachmentService.getAttachments(cardId);

        return ResponseEntity.ok(responseDtoList);
    }
}

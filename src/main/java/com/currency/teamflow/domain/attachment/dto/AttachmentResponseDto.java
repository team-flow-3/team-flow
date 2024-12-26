package com.currency.teamflow.domain.attachment.dto;

import com.currency.teamflow.domain.attachment.entity.Attachment;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class AttachmentResponseDto {

    private final String fileName;

    private final String fileUrl;

    private final LocalDateTime createdAt;

    public AttachmentResponseDto(String fileName, String fileUrl, LocalDateTime createdAt) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.createdAt = createdAt;
    }

    public static AttachmentResponseDto toDto(Attachment attachment) {
        return new AttachmentResponseDto(
                attachment.getFileName(),
                attachment.getFileUrl(),
                attachment.getCreatedAt()
        );
    }
}

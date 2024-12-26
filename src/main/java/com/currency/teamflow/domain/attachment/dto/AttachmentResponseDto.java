package com.currency.teamflow.domain.attachment.dto;

import com.currency.teamflow.domain.attachment.entity.Attachment;
import lombok.Getter;


@Getter
public class AttachmentResponseDto {

    private final String fileName;

    private final String fileUrl;

    public AttachmentResponseDto(String fileName,String fileUrl) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
    }

    public static AttachmentResponseDto toDto(Attachment attachment) {
        return new AttachmentResponseDto(
                attachment.getFileName(),
                attachment.getFileUrl()
        );
    }
}

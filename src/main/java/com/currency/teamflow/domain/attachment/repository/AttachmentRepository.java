package com.currency.teamflow.domain.attachment.repository;

import com.currency.teamflow.domain.attachment.entity.Attachment;
import com.currency.teamflow.global.error.errorcode.ErrorCode;
import com.currency.teamflow.global.error.exception.CustomException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    List<Attachment> findAllByCardCardId(Long cardId);

    default Attachment findByIdOrElseThrow(Long attachmentId) {
        return findById(attachmentId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
    }
}

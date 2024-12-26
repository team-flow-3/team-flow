package com.currency.teamflow.domain.attachment.service;

import com.currency.teamflow.domain.attachment.dto.AttachmentResponseDto;
import com.currency.teamflow.domain.attachment.entity.Attachment;
import com.currency.teamflow.domain.attachment.repository.AttachmentRepository;
import com.currency.teamflow.domain.card.entity.Card;
import com.currency.teamflow.domain.card.repository.CardRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AttachmentService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AttachmentRepository attachmentRepository;
    private final CardRepository cardRepository;

    private final S3Client s3Client;

    public AttachmentService(AttachmentRepository attachmentRepository, CardRepository cardRepository, S3Client s3Client) {
        this.attachmentRepository = attachmentRepository;
        this.cardRepository = cardRepository;
        this.s3Client = s3Client;
    }

    /**
     * 첨부파일 생성 서비스 메서드
     *
     * @param cardId 카드 식별자
     * @param files 첨부파일
     * @return List<AttachmentResponseDto>
     * @throws IOException
     */
    @Transactional
    public List<AttachmentResponseDto> createAttachments(Long cardId, List<MultipartFile> files) throws IOException {
        Card card = cardRepository.findByIdOrElseThrow(cardId);

        List<Attachment> attachmentList = new ArrayList<>();

        // 파일 s3에 업로드하고 DB에 정보 저장
        for(MultipartFile file : files) {
            String originalFilename = file.getOriginalFilename();

            if(originalFilename == null) {
                continue;
            }

            // 확장자 추출
            String extension = getExtension(originalFilename);

            // 고유 파일 이름 생성
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

            // S3에 파일 업로드 요청 생성
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(fileName)
                    .contentType(file.getContentType())
                    .build();

            // S3에 파일 업로드
            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            // S3 서버 객체 URL 가져오기
            String fileUrl = getPublicUrl(fileName);

            // 첨부파일 객체 생성
            Attachment attachment = new Attachment(file.getOriginalFilename(), fileName, extension, fileUrl, card);

            // 첨부파일 DB에 저장
            attachmentRepository.save(attachment);
            attachmentList.add(attachment);
        }

        return attachmentList.stream().map(AttachmentResponseDto::toDto).toList();
    }

    // 확장자 추출 메서드
    private static String getExtension(String originalFilename) {
        String extension;
        if(originalFilename.endsWith(".png")) {
            extension = "png";
        }
        else if(originalFilename.endsWith(".jpg")) {
            extension = "jpg";
        }
        else if(originalFilename.endsWith(".csv")) {
            extension = "csv";
        }
        else if(originalFilename.endsWith(".pdf")) {
            extension = "pdf";
        }
        else {
            throw new IllegalArgumentException("Unsupported file type: " + originalFilename);
        }
        return extension;
    }

    // S3 서버 경로 가져오는 메서드
    private String getPublicUrl(String fileName) {
        return String.format("https://%s.s3.amazonaws.com/%s", bucket, fileName);
    }

    /**
     * 첨부파일 조회 서비스 메서드
     *
     * @param cardId 카드 식별자
     * @return List<AttachmentResponseDto>
     */
    public List<AttachmentResponseDto> getAttachments(Long cardId) {

        List<Attachment> attachmentList = attachmentRepository.findAllByCardCardId(cardId);

        return attachmentList.stream().map(AttachmentResponseDto::toDto).toList();
    }

    /**
     * 첨부파일 삭제 서비스 메서드
     *
     * @param attachmentId 첨부파일 식별자
     */
    public void deleteAttachment(Long attachmentId) {

        Attachment attachment = attachmentRepository.findByIdOrElseThrow(attachmentId);

        // S3에 파일 업로드
        s3Client.deleteObject(DeleteObjectRequest.builder().bucket(bucket).key(attachment.getUuidFileName()).build());

        attachmentRepository.deleteById(attachmentId);
    }
}

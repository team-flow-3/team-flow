package com.currency.teamflow.global.alarm;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.model.block.Blocks;
import com.slack.api.model.block.composition.MarkdownTextObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
@Configuration
public class AlarmService {

    // 정보 가져오기
    @Value("${slack.bot-token}")
    private String token;
    @Value("${slack.channel.monitor}")
    private String channel;

    /**
     * 알람 서비스 로직
     * @param message
     */
    public void AlarmMessage(String message) {

        // properties 파일은 Spring에서 기본적으로 ISO_8859_1로 인코딩 되기 때문에 charset으로 변경.
        channel = new String(channel.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

        try {
            // Slack 메시지 블록 생성
            var blocks = Blocks.asBlocks(
                    Blocks.section(section ->
                            section.text(MarkdownTextObject.builder()
                                    .text(message) // 메시지를 마크다운 형식으로 처리
                                    .build())),
                    Blocks.divider()
            );

            // Slack API Client 생성
            MethodsClient methods = Slack.getInstance().methods(token);

            // Slack 메시지 전송 요청
            ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                    .channel(channel)
                    .text("백업 내용")
                    .blocks(blocks)
                    .build();

            methods.chatPostMessage(request);
        } catch (SlackApiException | IOException e) {
            throw new RuntimeException("Slack 메시지 전송 실패: " + e.getMessage());
        }
    }
}
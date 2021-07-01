package com.gabia.emailservice.service;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.gabia.emailservice.dto.request.SendEmailRequest;
import com.gabia.emailservice.sender.CommonEmailSender;
import com.gabia.emailservice.util.MemoryAppender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private CommonEmailSender commonEmailSender;

    @InjectMocks
    private EmailService emailService;

    private MemoryAppender memoryAppender;

    @BeforeEach
    public void setup() {
        Logger logger = (Logger) LoggerFactory.getLogger(EmailService.class);
        memoryAppender = new MemoryAppender();
        memoryAppender.setContext((LoggerContext) LoggerFactory.getILoggerFactory());
        logger.setLevel(Level.INFO);
        logger.addAppender(memoryAppender);
        memoryAppender.start();
    }

    @Test
    void 메일_발송_성공() throws Exception {
        //given
        String sender = "nameks17@gmail.com";
        List<String> raws = Arrays.asList("nameks@naver.com");
        String title = "제목";
        String content = "내용";
        String traceId = "abc";
        Long userId = 1L;

        SendEmailRequest request = SendEmailRequest.builder()
                .sender(sender)
                .raws(raws)
                .title(title)
                .content(content)
                .userId(userId)
                .traceId(traceId)
                .build();

        doNothing().when(commonEmailSender).sendEmail(request);

        //when
        emailService.sendEmail(request);

        //then
        assertThat(memoryAppender.getSize()).isEqualTo(1);
        assertThat(memoryAppender.contains(String.format("%s: userId:%s traceId:%s massage:%s",
                "EmailService", request.getUserId(), request.getTraceId(), "메일 발송 성공"), Level.INFO)).isTrue();
    }

    @Test
    void 메일_발송_실패_인증하지_않은_발신자() throws Exception {
        //given
        String sender = "notverified@email.com";
        List<String> raws = Arrays.asList("nameks@naver.com");
        String title = "제목";
        String content = "내용";
        String traceId = "abc";
        Long userId = 1L;

        SendEmailRequest request = SendEmailRequest.builder()
                .sender(sender)
                .raws(raws)
                .title(title)
                .content(content)
                .userId(userId)
                .traceId(traceId)
                .build();

        doThrow(new Exception("인증된 발신자가 아닙니다")).when(commonEmailSender).sendEmail(request);

        //when
        emailService.sendEmail(request);

        //then
        assertThat(memoryAppender.getSize()).isEqualTo(1);
        assertThat(memoryAppender.contains(String.format("%s: userId:%s traceId:%s massage:%s",
                "EmailService", request.getUserId(), request.getTraceId(), "메일 발송 실패"), Level.ERROR)).isTrue();
    }

    @Test
    void 인증_메일_발송_성공() {
        //given
        String emailAddress = "nameks@naver.com";

        doNothing().when(commonEmailSender).sendVerifyEmail(emailAddress);

        //when
        emailService.sendVerifyEmail(emailAddress);

        //then
        assertThat(memoryAppender.getSize()).isEqualTo(1);
        assertThat(memoryAppender.contains("EmailService: 인증 메일 발송 완료", Level.INFO)).isTrue();
    }

}
package com.gabia.emailservice.service;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.gabia.emailservice.dto.request.AlarmMessage;
import com.gabia.emailservice.dto.request.SendEmailRequest;
import com.gabia.emailservice.sender.CommonEmailSender;
import com.gabia.emailservice.util.LogSender;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private CommonEmailSender commonEmailSender;

    @InjectMocks
    private EmailService emailService;

    private MemoryAppender memoryAppender;

    private String sender = "nameks17@gmail.com";
    private List<String> addresses = Arrays.asList("nameks@naver.com");
    private String title = "제목";
    private String content = "내용";
    private String traceId = "abc";
    private Long userId = 1L;
    private Long groupId = 1L;

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
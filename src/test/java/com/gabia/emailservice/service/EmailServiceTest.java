package com.gabia.emailservice.service;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.gabia.emailservice.dto.request.SendEmailRequest;
import com.gabia.emailservice.dto.response.SendEmailResponse;
import com.gabia.emailservice.sender.CommonEmailSender;
import com.gabia.emailservice.util.MemoryAppender;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private CommonEmailSender commonEmailSender;

    @InjectMocks
    private EmailService emailService;

    private MemoryAppender memoryAppender;

    @BeforeEach
    public void setup() {
        Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        memoryAppender = new MemoryAppender();
        memoryAppender.setContext((LoggerContext) LoggerFactory.getILoggerFactory());
        logger.setLevel(Level.INFO);
        logger.addAppender(memoryAppender);
        memoryAppender.start();
    }

    @Test
    void 메일_발송_성공() throws Exception {
        //given
        SendEmailRequest request = SendEmailRequest.builder()
                .sender("nameks17@gmail.com")
                .raws(Lists.newArrayList("nameks@naver.com"))
                .title("테스트")
                .content("안녕하세요")
                .build();

        doNothing().when(commonEmailSender).sendEmail(request);

        //when
        emailService.sendEmail(request);

        //then
        assertThat(memoryAppender.getSize()).isEqualTo(1);
        assertThat(memoryAppender.contains("메일 발송 완료", Level.INFO)).isTrue();
    }

    @Test
    void 인증_메일_발송_성공() {
        //given
        String emailAddress = "nameks@naver.com";

        SendEmailResponse response = SendEmailResponse.builder()
                .message("ok")
                .build();

        doNothing().when(commonEmailSender).sendVerifyEmail(emailAddress);

        //when
        emailService.sendVerifyEmail(emailAddress);

        //then
        assertThat(memoryAppender.getSize()).isEqualTo(1);
        assertThat(memoryAppender.contains("인증 메일 발송 완료", Level.INFO)).isTrue();
    }

}
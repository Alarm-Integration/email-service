package com.gabia.emailservice.sender;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.MessageRejectedException;
import com.amazonaws.services.simpleemail.model.SendEmailResult;
import com.amazonaws.services.simpleemail.model.VerifyEmailIdentityRequest;
import com.amazonaws.services.simpleemail.model.VerifyEmailIdentityResult;
import com.gabia.emailservice.dto.request.SendEmailRequest;
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
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class AWSEmailSenderTest {

    @Mock
    private AmazonSimpleEmailService amazonSimpleEmailService;

    @InjectMocks
    private AWSEmailSender amazonEmailSender;

    private MemoryAppender memoryAppender;

    @BeforeEach
    public void setup() {
        Logger logger = (Logger) LoggerFactory.getLogger(AWSEmailSender.class);
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
        String title = "제목";
        String content = "내용";
        List<String> raws = Arrays.asList("nameks@naver.com");
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

        SendEmailResult sendEmailResult = new SendEmailResult().withMessageId("123");
        given(amazonSimpleEmailService.sendEmail(request.toAWSRequest())).willReturn(sendEmailResult);

        //when
        amazonEmailSender.sendEmail(request);

        //then
        assertThat(memoryAppender.getSize()).isEqualTo(1);
        assertThat(memoryAppender.contains(String.format("%s: userId:%s traceId:%s massage:%s massageId:%s",
                "AWSEmailSender", request.getUserId(), request.getTraceId(), "메일 발송 성공", sendEmailResult.getMessageId()), Level.INFO)).isTrue();
    }

    @Test
    void 메일_발송_실패_인증하지_않은_발신자() {
        //given
        String sender = "notverified@email.com";
        String title = "제목";
        String content = "내용";
        List<String> raws = Arrays.asList("nameks@naver.com");
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

        given(amazonSimpleEmailService.sendEmail(request.toAWSRequest()))
                .willThrow(new MessageRejectedException("Email address is not verified"));

        //when
        Throwable throwable = catchThrowable(() -> amazonEmailSender.sendEmail(request));

        //then
        assertThat(throwable).isInstanceOf(Exception.class).hasMessageContaining("인증된 발신자가 아닙니다");
        assertThat(memoryAppender.getSize()).isEqualTo(1);
        assertThat(memoryAppender.contains(String.format("%s: userId:%s traceId:%s massage:%s",
                "AWSEmailSender", request.getUserId(), request.getTraceId(), "인증된 발신자가 아닙니다"), Level.ERROR)).isTrue();
    }

    @Test
    void 인증_메일_발송_성공() {
        //given
        String emailAddress = "nameks@naver.com";

        VerifyEmailIdentityResult verifyEmailIdentityResult = mock(VerifyEmailIdentityResult.class);

        given(amazonSimpleEmailService.verifyEmailIdentity(new VerifyEmailIdentityRequest().withEmailAddress(emailAddress)))
                .willReturn(verifyEmailIdentityResult);

        //when
        amazonEmailSender.sendVerifyEmail(emailAddress);

        //then
        assertThat(memoryAppender.getSize()).isEqualTo(1);
        assertThat(memoryAppender.contains("AWSEmailSender: 인증 메일 발송 성공", Level.INFO)).isTrue();
    }
}
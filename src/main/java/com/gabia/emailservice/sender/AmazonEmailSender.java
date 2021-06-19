package com.gabia.emailservice.sender;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.VerifyEmailIdentityRequest;
import com.gabia.emailservice.dto.request.SendEmailRequest;
import com.gabia.emailservice.dto.response.SendEmailResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AmazonEmailSender implements CommonEmailSender {

    private final AmazonSimpleEmailService amazonSimpleEmailService;

    @Override
    public SendEmailResponse sendEmail(SendEmailRequest request){
        try {
            amazonSimpleEmailService.sendEmail(request.toSendRequestDto());
        }
        catch (Exception e){
            log.error("send email error message: {}",e.getMessage());

            if(e.getMessage().startsWith("Email address is not verified"))
                return SendEmailResponse.withMessage("Email address is not verified");

            return SendEmailResponse.withMessage(e.getMessage());
        }

        return SendEmailResponse.withMessage("메일 발송 완료");
    }

    @Override
    public SendEmailResponse sendVerifyEmail(String emailAddress) {
        VerifyEmailIdentityRequest request = new VerifyEmailIdentityRequest().withEmailAddress(emailAddress);

        amazonSimpleEmailService.verifyEmailIdentity(request);

        return SendEmailResponse.withMessage("인증 메일 발송 완료");
    }
}


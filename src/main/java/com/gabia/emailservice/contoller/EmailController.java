package com.gabia.emailservice.contoller;

import com.gabia.emailservice.dto.request.SendEmailRequest;
import com.gabia.emailservice.dto.request.SendVerifyEmailRequest;
import com.gabia.emailservice.dto.response.APIResponse;
import com.gabia.emailservice.dto.response.SendEmailResponse;
import com.gabia.emailservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/email")
    public ResponseEntity<?> sendEmail(@RequestBody SendEmailRequest sendEmailRequest){
        SendEmailResponse response = emailService.sendEmail(sendEmailRequest);

        return ResponseEntity.ok(APIResponse.withMessage(response.getMessage()));
    }

    @PostMapping("/verify-email")
    public ResponseEntity<?> sendVerifyEmail(@RequestBody SendVerifyEmailRequest request){
        SendEmailResponse response = emailService.sendVerifyEmail(request.getEmailAddress());

        return ResponseEntity.ok(APIResponse.withMessage(response.getMessage()));
    }

}

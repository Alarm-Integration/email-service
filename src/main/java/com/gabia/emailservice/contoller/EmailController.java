package com.gabia.emailservice.contoller;

import com.gabia.emailservice.dto.response.APIResponse;
import com.gabia.emailservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/email-service")
@RequiredArgsConstructor
@RestController
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/verify-email/{email}")
    public ResponseEntity<?> sendVerifyEmail(@PathVariable String email) {
        emailService.sendVerifyEmail(email);

        return ResponseEntity.ok(APIResponse.withMessage("인증 메일 발송 완료"));
    }

}

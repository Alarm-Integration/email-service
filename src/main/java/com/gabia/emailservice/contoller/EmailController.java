package com.gabia.emailservice.contoller;

import com.gabia.emailservice.dto.response.APIResponse;
import com.gabia.emailservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/verify-email/{email}")
    public ResponseEntity<?> sendVerifyEmail(@PathVariable String email) {
        log.info("test code");
        emailService.sendVerifyEmail(email);

        return ResponseEntity.ok(APIResponse.withMessage("인증 메일 발송 완료"));
    }

}

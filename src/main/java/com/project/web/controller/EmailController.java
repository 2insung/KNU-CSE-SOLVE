package com.project.web.controller;

import com.project.web.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @GetMapping("/email")
    public ResponseEntity<String> sendEmail(@RequestParam(name = "username") String email) throws Exception {
        String code = emailService.sendSimpleMessage(email);
        return ResponseEntity.ok(code);
    }

    @PostMapping("/checkCode")
    public ResponseEntity<Boolean> checkCode(
            @RequestParam(name = "email") String email,
            @RequestParam(name = "code") String code) {
        return ResponseEntity.ok(emailService.checkCode(email, code));
    }
}

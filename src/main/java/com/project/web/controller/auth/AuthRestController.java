package com.project.web.controller.auth;

import com.project.web.controller.auth.dto.rest.SignUpRequestDto;
import com.project.web.controller.auth.dto.rest.SignUpResponseDto;
import com.project.web.service.auth.SignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// 로그인 및 회원가입 REST API
@RestController
@RequiredArgsConstructor
public class AuthRestController {
    private final SignUpService signUpService;

    @PostMapping("/api/signup")
    public ResponseEntity<SignUpResponseDto> signup(@RequestBody SignUpRequestDto signUpRequestDto) {
        String username = signUpRequestDto.getUsername();
        String password = signUpRequestDto.getPassword();
        String nickname = signUpRequestDto.getNickname();

        signUpService.signup(username, password, nickname);

        return ResponseEntity.ok(
                SignUpResponseDto.builder()
                        .isSuccess(true)
                        .build()
        );
    }
}

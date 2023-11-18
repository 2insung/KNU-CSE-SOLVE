package com.project.web.controller.auth;

import com.project.web.controller.auth.dto.rest.SignUpRequestDto;
import com.project.web.controller.auth.dto.rest.SignUpResponseDto;
import com.project.web.exception.Error400Exception;
import com.project.web.service.auth.SignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

// 로그인 및 회원가입 REST API
@RestController
@RequiredArgsConstructor
public class AuthRestController {
    private final SignUpService signUpService;

    @PostMapping("/api/signup")
    public ResponseEntity<SignUpResponseDto> signup(@Valid @RequestBody SignUpRequestDto signUpRequestDto,
                                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldError().getDefaultMessage();
            throw new Error400Exception(errorMessage);
        }

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

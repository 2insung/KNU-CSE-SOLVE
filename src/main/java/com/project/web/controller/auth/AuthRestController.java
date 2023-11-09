package com.project.web.controller.auth;

import com.project.web.controller.auth.dto.*;
import com.project.web.controller.community.dto.post.SavePostResponseDto;
import com.project.web.service.auth.SignUpEmailService;
import com.project.web.service.auth.SignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthRestController {
    private final SignUpService signUpService;
    private final SignUpEmailService signUpEmailService;

    @PostMapping("/api/signup")
    public ResponseEntity<SignUpResponseDto> signup(@RequestBody SignUpRequestDto signUpRequestDto) {
        String username = signUpRequestDto.getUsername();
        String password = signUpRequestDto.getPassword();
        String nickname = signUpRequestDto.getNickname();
        signUpService.signup(username, password, nickname);

        return ResponseEntity.ok(
                SignUpResponseDto.builder()
                        .result(true)
                        .build()
        );
    }

    @PostMapping("/api/send-email")
    public ResponseEntity<SendEmailResponseDto> sendEmail(@RequestBody SendEmailRequestDto sendEmailRequestDto) throws Exception {
        String username = sendEmailRequestDto.getUsername();
        String nickname = sendEmailRequestDto.getNickname();

        Boolean isExistingNickname = signUpService.checkExistingNickname(nickname);
        Boolean isExistingUsername = signUpService.checkExistingUsername(username);
        String code = signUpEmailService.sendEmail(username);

        return ResponseEntity.ok(
                SendEmailResponseDto.builder()
                        .isExistingUsername(isExistingUsername)
                        .isExistingNickname(isExistingNickname)
                        .code(code)
                        .build()
        );
    }

    @PostMapping("/api/check-code")
    public ResponseEntity<CheckCodeResponseDto> checkCode(@RequestBody CheckCodeRequestDto checkCodeRequestDto) {
        String username = checkCodeRequestDto.getUsername();
        String code = checkCodeRequestDto.getCode();
        Boolean checkCode = signUpEmailService.checkCode(username, code);
        return ResponseEntity.ok(
                CheckCodeResponseDto.builder()
                        .result(checkCode)
                        .build()
        );
    }
}

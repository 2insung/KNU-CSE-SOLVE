package com.project.web.controller;

import com.project.web.controller.dto.PrincipalDetails;
import com.project.web.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @GetMapping("/me")
    public ResponseEntity<String> me(@AuthenticationPrincipal PrincipalDetails principal){
        String nickname = null;
        if(principal != null){
            nickname = authService.me(principal.getUsername());
        }
        return ResponseEntity.ok(nickname);
    }
}

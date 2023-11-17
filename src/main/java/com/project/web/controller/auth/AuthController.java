package com.project.web.controller.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

// 로그인 및 회원가입 뷰 반환
@Controller
@RequiredArgsConstructor
public class AuthController {
    @GetMapping("/login")
    public String login(@RequestParam(name = "error", required = false) String error,
                        @RequestParam(name = "exception", required = false) String exception,
                        Model model) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "LoginPage";
    }

    @GetMapping("/signup")
    public String SignUpPage() {
        return "SignUpPage";
    }
}

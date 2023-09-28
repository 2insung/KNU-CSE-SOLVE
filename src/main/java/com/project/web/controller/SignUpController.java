package com.project.web.controller;

import com.project.web.controller.dto.signup.SignUpRequestDto;
import com.project.web.service.SignUpEmailService;
import com.project.web.service.SignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class SignUpController {
    private final SignUpService signUpService;
    private final SignUpEmailService signUpEmailService;

    @PostMapping("/public/signup/save")
    public ModelAndView signup(@ModelAttribute SignUpRequestDto signUpRequestDto) {
        ModelAndView modelAndView = new ModelAndView();
        signUpService.signup(signUpRequestDto);
        modelAndView.setViewName("redirect:/login-page");
        return modelAndView;
    }

    @PostMapping("/public/signup/send-email")
    public ResponseEntity<String> sendEmail(@RequestParam(name = "nickname") String nickname,
                                            @RequestParam(name = "username") String username) throws Exception {
        Boolean isExistingNickname = signUpService.checkExistingNickname(nickname);
        Boolean isExistingUsername = signUpService.checkExistingUsername(username);

        if (isExistingNickname) {
            return ResponseEntity.ok("InvalidNickname");
        }

        if (isExistingUsername) {
            return ResponseEntity.ok("InvalidUsername");
        }

        String code = signUpEmailService.sendEmail(username);
        return ResponseEntity.ok(code);
    }

    @PostMapping("/public/signup/check-code")
    public ResponseEntity<Boolean> checkCode(
            @RequestParam(name = "email") String email,
            @RequestParam(name = "code") String code) {
        return ResponseEntity.ok(signUpEmailService.checkCode(email, code));
    }

}

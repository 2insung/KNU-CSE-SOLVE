package com.project.web.controller;

import com.project.web.controller.dto.SignUpRequestDto;
import com.project.web.service.EmailService;
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
    private final EmailService emailService;
    @GetMapping("/signup")
    public ModelAndView SignUp(){
        ModelAndView modelAndView = new ModelAndView("signup");
        return modelAndView;
    }

    @PostMapping("/signup")
    public ModelAndView signup(@ModelAttribute SignUpRequestDto signUpRequestDto){
        signUpService.signup(signUpRequestDto);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/login");
        return modelAndView;
    }

    @PostMapping("/check")
    public ResponseEntity<String> check(@RequestParam(name="nickname") String nickname,
                                         @RequestParam(name="username") String username) throws Exception{
        Boolean isVaildNickname = signUpService.checkNickname(nickname);
        Boolean isValidUsername = signUpService.checkUsername(username);

        if(!isVaildNickname){
            return ResponseEntity.ok("InvalidNickname");
        }

        if(!isValidUsername){
            return ResponseEntity.ok("InvalidUsername");
        }

        String code = emailService.sendSimpleMessage(username);
        return ResponseEntity.ok(code);
    }

}

package com.project.web.controller;

import com.project.web.controller.dto.SignUpRequestDto;
import com.project.web.service.SignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class SignUpController {
    private final SignUpService signUpService;
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
}

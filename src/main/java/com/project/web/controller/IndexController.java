package com.project.web.controller;

import com.project.web.controller.dto.PrincipalDetails;
import com.project.web.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class IndexController {
    private final AuthService authService;
    @GetMapping("/index")
    public ModelAndView index(@AuthenticationPrincipal PrincipalDetails principalDetails){
        ModelAndView modelAndView = new ModelAndView();
        if(principalDetails != null){
            modelAndView.addObject("status", true);
            modelAndView.addObject("nickname", authService.me(principalDetails.getUsername()));
            modelAndView.addObject("username", principalDetails.getUsername());
        }
        else{
            modelAndView.addObject("status", false);
        }
        modelAndView.setViewName("index");
        return modelAndView;
    }
}

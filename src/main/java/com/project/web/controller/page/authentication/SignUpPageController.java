package com.project.web.controller.page.authentication;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SignUpPageController {
    @GetMapping("/signup")
    public String SignUpPage(){
        return "SignUpPage";
    }
}

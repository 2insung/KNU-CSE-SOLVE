package com.project.web.controller.page;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SignUpPageController {
    @GetMapping("/signup-page")
    public ModelAndView SignUpPage(){
        ModelAndView modelAndView = new ModelAndView("SignUpPage");
        return modelAndView;
    }
}

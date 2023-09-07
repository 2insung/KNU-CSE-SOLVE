package com.project.web.controller.page;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginPageController {
    @GetMapping("/login-page")
    public ModelAndView login(@RequestParam(name="error", required = false) String error,
                              @RequestParam(name="exception", required = false) String exception){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("error", error);
        modelAndView.addObject("exception", exception);
        modelAndView.setViewName("LoginPage");
        return modelAndView;
    }
}

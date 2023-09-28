package com.project.web.controller.page.authentication;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginPageController {
    @GetMapping("/login")
    public String login(@RequestParam(name="error", required = false) String error,
                        @RequestParam(name="exception", required = false) String exception,
                        Model model){
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "LoginPage";
    }
}

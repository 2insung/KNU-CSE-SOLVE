package com.project.web.handler;

import com.project.web.exception.SignUpException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class SignUpExceptionHandler {
    @ExceptionHandler(SignUpException.class)
    public String handleSignUpException(HttpServletResponse response, SignUpException exception, Model model){
        model.addAttribute("code", 409);
        model.addAttribute("error", exception.getMessage());
        response.setStatus(HttpServletResponse.SC_CONFLICT);
        return "redirect:/signup";
    }
}

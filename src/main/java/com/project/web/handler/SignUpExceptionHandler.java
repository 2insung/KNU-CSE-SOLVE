package com.project.web.handler;

import com.project.web.exception.SignUpException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class SignUpExceptionHandler {
    @ExceptionHandler(SignUpException.class)
    public ModelAndView handleSignUpException(SignUpException exception){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("error", exception.getMessage());
        modelAndView.setViewName("500");
        return modelAndView;
    }
}

package com.project.web.handler;

import com.project.web.exception.Error500Exception;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class Error500ExceptionHandler {
    @ExceptionHandler(Error500Exception.class)
    public ModelAndView handleError500Exception(HttpServletResponse response, Error500Exception exception){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error/500");
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return modelAndView;
    }
}

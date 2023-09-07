package com.project.web.handler;

import com.project.web.exception.Error404Exception;
import com.project.web.exception.SignUpException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class Error404ExceptionHandler {
    @ExceptionHandler(Error404Exception.class)
    public ModelAndView handleError404Exception(HttpServletResponse response, Error404Exception exception){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error/404");
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return modelAndView;
    }
}

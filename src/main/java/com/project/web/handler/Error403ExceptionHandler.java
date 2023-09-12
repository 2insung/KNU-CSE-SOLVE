package com.project.web.handler;

import com.project.web.exception.Error403Exception;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class Error403ExceptionHandler {
    @ExceptionHandler(Error403Exception.class)
    public ModelAndView handleError404Exception(HttpServletResponse response, Error403Exception exception){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error/403");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        return modelAndView;
    }
}

package com.project.web.handler;

import com.project.web.exception.Error400Exception;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class Error400ExceptionHandler {
    @ExceptionHandler(Error400Exception.class)
    public ModelAndView handleError404Exception(HttpServletResponse response, Error400Exception exception){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error/400");
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return modelAndView;
    }
}

package com.project.web.handler;

import com.project.web.exception.Error400Exception;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class Error400ExceptionHandler {
    @ExceptionHandler(Error400Exception.class)
    public Object handleError400Exception(HttpServletRequest request, HttpServletResponse response, Error400Exception exception){
        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(exception.getMessage());
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "error/400";
        }
    }
}
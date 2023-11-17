package com.project.web.handler;

import com.project.web.exception.Error500Exception;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class Error500ExceptionHandler {
    @ExceptionHandler(Error500Exception.class)
    public Object handleError404Exception(HttpServletRequest request, HttpServletResponse response, Error500Exception exception){
        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(exception.getMessage());
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return "error/500";
        }
    }
}

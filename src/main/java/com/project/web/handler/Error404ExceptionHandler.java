package com.project.web.handler;

import com.project.web.exception.Error404Exception;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class Error404ExceptionHandler {
    @ExceptionHandler(Error404Exception.class)
    public Object handleError404Exception(HttpServletRequest request, HttpServletResponse response, Error404Exception exception){
        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body(exception.getMessage());
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return "error/404";
        }
    }
}

package com.project.web.handler;

import com.project.web.exception.Error403Exception;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class Error403ExceptionHandler {
    @ExceptionHandler(Error403Exception.class)
    public Object handleError403Exception(HttpServletRequest request, HttpServletResponse response, Error403Exception exception) {
        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).body(exception.getMessage());
        }
        else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return "error/403";
        }
    }
}

package com.insung.knucsesolve.handler;

import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class DataAccessExceptionHandler {
    @ExceptionHandler(DataAccessException.class)
    public Object handleDataAccessException(HttpServletRequest request, HttpServletResponse response) {
        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("알 수 없는 에러입니다.");
        }
        else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "error/400";
        }
    }
}

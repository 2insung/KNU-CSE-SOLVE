package com.project.web.handler;

import com.project.web.exception.SignUpException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class SignUpExceptionHandler {
    @ExceptionHandler(SignUpException.class)
    public ResponseEntity<Map<String, String>> handleSignUpException(SignUpException exception){
        Map<String, String> errors = new HashMap<>();
        errors.put(exception.getErrorKey(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}

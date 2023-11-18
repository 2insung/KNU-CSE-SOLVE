package com.project.web.handler;

import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class FileUploadExceptionHandler {
    @ExceptionHandler(FileSizeLimitExceededException.class)
    public Object handleError404Exception(HttpServletRequest request, HttpServletResponse response) {
        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body("1MB 이하의 파일만 가능합니다.");
        }
        else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return "error/500";
        }
    }
}

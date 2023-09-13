package com.project.web.exception;

public class Error400Exception extends RuntimeException {

    public Error400Exception(String errorMessage) {
        super(errorMessage);
    }

    public Error400Exception(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }

}
package com.project.web.exception;

public class SignUpException extends RuntimeException{

    public SignUpException(String errorMessage){
        super(errorMessage);
    }

    public SignUpException(String errorMessage, Throwable cause){
        super(errorMessage, cause);
    }

}

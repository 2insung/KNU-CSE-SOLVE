package com.project.web.exception;

public class SignUpException extends RuntimeException{
    private final String errorKey;

    public SignUpException(String errorKey, String errorMessage){
        super(errorMessage);
        this.errorKey = errorKey;
    }

    public SignUpException(String errorKey, String errorMessage, Throwable cause){
        super(errorMessage, cause);
        this.errorKey = errorKey;
    }

    public String getErrorKey(){
        return errorKey;
    }
}

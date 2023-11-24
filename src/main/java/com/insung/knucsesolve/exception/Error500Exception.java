package com.insung.knucsesolve.exception;

public class Error500Exception extends RuntimeException {

    public Error500Exception(String errorMessage) {
        super(errorMessage);
    }

    public Error500Exception(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }


}
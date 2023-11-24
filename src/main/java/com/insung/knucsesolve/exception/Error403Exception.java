package com.insung.knucsesolve.exception;

public class Error403Exception  extends RuntimeException {

    public Error403Exception(String errorMessage) {
        super(errorMessage);
    }

    public Error403Exception(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }

}

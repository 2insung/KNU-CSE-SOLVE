package com.insung.knucsesolve.exception;

public class Error404Exception extends RuntimeException {

    public Error404Exception(String errorMessage) {
        super(errorMessage);
    }

    public Error404Exception(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }


}

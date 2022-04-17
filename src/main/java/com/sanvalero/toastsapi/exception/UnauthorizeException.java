package com.sanvalero.toastsapi.exception;

public class UnauthorizeException extends Exception {
    private final static String DEFAULT_ERROR_MESSAGE = "Unauthorize exception";

    public UnauthorizeException(String message) {
        super(message);
    }

    public UnauthorizeException() {
        super(DEFAULT_ERROR_MESSAGE);
    }
}

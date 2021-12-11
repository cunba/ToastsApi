package com.sanvalero.toastsapi.exception;

public class NotFoundException extends Exception {
    private final static String DEFAULT_ERROR_MESSAGE = "Not found exception";

    public <T>NotFoundException(String message) {
        super(message);
    }

    public <T>NotFoundException() {
        super(DEFAULT_ERROR_MESSAGE);
    }
}

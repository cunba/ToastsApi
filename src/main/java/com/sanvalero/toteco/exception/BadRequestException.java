package com.sanvalero.toteco.exception;

public class BadRequestException extends Exception {
    private final static String DEFAULT_ERROR_MESSAGE = "Bad request exception";

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException() {
        super(DEFAULT_ERROR_MESSAGE);
    }
}

package ru.dimax.main.exception;

public class RequestApplicationException extends RuntimeException {
    public RequestApplicationException(String message) {
        super(message);
    }
}

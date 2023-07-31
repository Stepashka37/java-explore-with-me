package ru.dimax.main.exception;

public class EntityValidationException extends RuntimeException {
    public EntityValidationException(String message) {
        super(message);
    }
}

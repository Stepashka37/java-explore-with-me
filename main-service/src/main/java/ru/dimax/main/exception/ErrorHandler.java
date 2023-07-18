package ru.dimax.main.exception;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    private final String dateTimePattern = "yyyy-MM-dd HH:mm:ss";

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse notFoundException(final RuntimeException exception) {
        log.error("404: " + exception.getMessage());
        return new ErrorResponse("NOT_FOUND",
                "The required object was not found.",
                exception.getMessage(),
                String.valueOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern(dateTimePattern))));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse constaintViolationException(final RuntimeException exception) {
        log.error("409: " + exception.getMessage());
        return new ErrorResponse("CONFLICT",
                "Integrity constraint has been violated.",
                exception.getMessage(),
                String.valueOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern(dateTimePattern))));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse validationException(final RuntimeException exception) {
        log.error("409: " + exception.getMessage());
        return new ErrorResponse("BAD_REQUEST",
                "Incorrectly made request.",
                exception.getMessage(),
                String.valueOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern(dateTimePattern))));
    }


}

package ru.dimax.main.exception;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import javax.ws.rs.Produces;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    private final String dateTimePattern = "yyyy-MM-dd HH:mm:ss";

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse notFoundException(final RuntimeException exception) {
        log.error("404: " + exception.getMessage());
        return new ErrorResponse(HttpStatus.NOT_FOUND.name(),
                "The required object was not found.",
                exception.getMessage(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern(dateTimePattern)));
    }

    @ExceptionHandler({ConstraintViolationException.class, AlreadyPublishedException.class, RequestApplicationException.class, ConflictException.class })
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse constraintViolationException(final RuntimeException exception) {
        log.error("409: " + exception.getMessage());
        return new ErrorResponse(HttpStatus.CONFLICT.name(),
                "Integrity constraint has been violated.",
                exception.getMessage(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern(dateTimePattern)));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, EventValidationException.class, TimeValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse argumentValidationException(final RuntimeException exception) {
        log.error("400: " + exception.getMessage());
        return new ErrorResponse(HttpStatus.BAD_REQUEST.name(),
                "Incorrectly made request.",
                exception.getMessage(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern(dateTimePattern)));
    }

    @ExceptionHandler({ConditionException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse validationException(final RuntimeException exception) {
        log.error("403: " + exception.getMessage());
        return new ErrorResponse(HttpStatus.FORBIDDEN.name(),
                "For the requested operation the conditions are not met.",
                exception.getMessage(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern(dateTimePattern)));
    }


}

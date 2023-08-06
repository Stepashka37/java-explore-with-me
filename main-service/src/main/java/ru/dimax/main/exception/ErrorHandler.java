package ru.dimax.main.exception;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.dimax.main.constants.Constants;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {


    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse notFoundException(final RuntimeException exception) {
        log.error("404: " + exception.getMessage());
        return new ErrorResponse(HttpStatus.NOT_FOUND.name(),
                "The required object was not found.",
                exception.getMessage(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern(Constants.DATE_TIME_PATTERN)));
    }

    @ExceptionHandler({ConstraintViolationException.class, AlreadyPublishedException.class, RequestApplicationException.class, ConflictException.class })
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse constraintViolationException(final RuntimeException exception) {
        log.error("409: " + exception.getMessage());
        return new ErrorResponse(HttpStatus.CONFLICT.name(),
                "Integrity constraint has been violated.",
                exception.getMessage(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern(Constants.DATE_TIME_PATTERN)));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse methodArgumentNoValidExc(final MethodArgumentNotValidException exception) {
        log.error("400: " + exception.getMessage());
        return new ErrorResponse(HttpStatus.BAD_REQUEST.name(),
                "Incorrectly made request.",
                String.format("Field: %s. Error: %s. Value: %s",
                        exception.getBindingResult().getFieldError().getField(),
                        exception.getBindingResult().getFieldError().getDefaultMessage(),
                        exception.getBindingResult().getFieldError().getRejectedValue()),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern(Constants.DATE_TIME_PATTERN)));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse entityValidationExceptionHandler(final EntityValidationException e) {
        log.error("400: " + e.getMessage());
        return new ErrorResponse(HttpStatus.BAD_REQUEST.name(),
                "Incorrectly made request.",
                e.getMessage(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern(Constants.DATE_TIME_PATTERN)));
    }



    @ExceptionHandler({ConditionException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse validationException(final RuntimeException exception) {
        log.error("403: " + exception.getMessage());
        return new ErrorResponse(HttpStatus.FORBIDDEN.name(),
                "For the requested operation the conditions are not met.",
                exception.getMessage(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern(Constants.DATE_TIME_PATTERN)));
    }


}

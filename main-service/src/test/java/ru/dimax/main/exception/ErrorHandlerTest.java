package ru.dimax.main.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ErrorHandlerTest {

    private ErrorHandler errorHandler;

    @BeforeEach
    void setUp() {
        errorHandler = new ErrorHandler();
    }

    @Test
    void itShouldHandleUserNotFoundExc() {
        // Given
        UserNotFoundException userNotFoundException = new UserNotFoundException("User not found");
        ErrorResponse result = errorHandler.notFoundException(userNotFoundException);
        // When
        // Then
        System.out.println(result);
        assertThat(result).isNotNull();
        assertThat(result).hasFieldOrProperty("status");
        assertThat(result).hasFieldOrProperty("reason");
        assertThat(result).hasFieldOrProperty("description");
        assertThat(result).hasFieldOrProperty("timestamp");

    }
}
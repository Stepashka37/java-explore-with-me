package ru.dimax.main.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.dimax.main.model.dtos.NewUserRequest;
import ru.dimax.main.model.dtos.UserDto;
import ru.dimax.main.service.UserService;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminController.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private final ObjectMapper mapper = new ObjectMapper();
    private final Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.registerModule(new JavaTimeModule());
    }

    @SneakyThrows
    @Test
    void itShouldCreateUser() {
        // Given
        NewUserRequest userRequest = NewUserRequest.builder()
                .name(faker.name().fullName())
                .email(String.format("%s@practicum.ru", faker.funnyName()))
                .build();

        UserDto  userDto = UserDto.builder()
                .id(1L)
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .build();

        when(userService.createUser(userRequest)).thenReturn(userDto);
        // When
        // Then
        mockMvc.perform(post("/admin/users")
                        .content(mapper.writeValueAsString(userRequest))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(userDto.getId()), Long.class));

    }

    @SneakyThrows
    @Test
    void itShouldNotCreateUserWithInvalidEmail() {
        // Given
        NewUserRequest userRequest = NewUserRequest.builder()
                .name(faker.name().fullName())

                .build();

        UserDto  userDto = UserDto.builder()
                .id(1L)
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .build();


        // When
        // Then
        mockMvc.perform(post("/admin/users")
                        .content(mapper.writeValueAsString(userRequest))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }



}
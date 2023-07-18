package ru.dimax.main.model.dtos;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Builder
@Data
public class NewUserRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String name;
}

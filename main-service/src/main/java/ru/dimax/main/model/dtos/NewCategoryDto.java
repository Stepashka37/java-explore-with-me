package ru.dimax.main.model.dtos;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class NewCategoryDto {

    @NotBlank
    private String name;
}

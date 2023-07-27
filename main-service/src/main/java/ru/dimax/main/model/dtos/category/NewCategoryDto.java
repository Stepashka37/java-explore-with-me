package ru.dimax.main.model.dtos.category;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@ToString
public class NewCategoryDto {

    public NewCategoryDto() {
    }

    public NewCategoryDto(String name) {
        this.name = name;
    }

    @NotBlank
    @Size(min = 1, max = 50)
    private String name;
}

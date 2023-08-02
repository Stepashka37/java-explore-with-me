package ru.dimax.main.model.dtos.geolocation;


import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
public class NewGeoLocationDto {

    @NotBlank
    @Size(min = 3, max = 100)
    private String name;

    @NotNull
    private Float lat;

    @NotNull
    private Float lon;

}

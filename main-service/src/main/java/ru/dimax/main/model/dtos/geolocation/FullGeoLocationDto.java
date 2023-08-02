package ru.dimax.main.model.dtos.geolocation;

import lombok.Builder;
import lombok.Data;
import ru.dimax.main.model.dtos.event.EventFullDto;

import java.util.List;

@Data
@Builder
public class FullGeoLocationDto {

    private Long id;

    private String name;

    private Float lat;

    private Float lon;

    private List<EventFullDto> events;
}

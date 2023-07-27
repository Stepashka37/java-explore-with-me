package ru.dimax.main.model.dtos.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import ru.dimax.main.model.Location;
import ru.dimax.main.model.State;
import ru.dimax.main.model.dtos.category.CategoryDto;
import ru.dimax.main.model.dtos.user.UserShortDto;

import java.time.LocalDateTime;

@Builder
@Data
public class EventFullDto {

    private String annotation;

    private CategoryDto category;

    public Long confirmedRequests;

    public LocalDateTime createdOn;

    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    private Long id;

    private UserShortDto initiator;

    private Location location;

    private Boolean paid;

    private Long participantLimit;

    private LocalDateTime publishedOn;

    private Boolean requestModeration;

    private State state;

    private String title;

    private Integer views;
}

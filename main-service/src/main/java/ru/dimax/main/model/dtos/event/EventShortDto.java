package ru.dimax.main.model.dtos.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import ru.dimax.main.constants.Constants;
import ru.dimax.main.model.dtos.category.CategoryDto;
import ru.dimax.main.model.dtos.user.UserShortDto;

import java.time.LocalDateTime;

@Builder
@Data
public class EventShortDto {

    private String annotation;

    private CategoryDto category;

    public Long confirmedRequests;

    @JsonFormat(pattern = Constants.DATE_TIME_PATTERN)
    private LocalDateTime eventDate;

    private Long id;

    private UserShortDto initiator;

    private Boolean paid;

    private String title;

    private Integer views;
}

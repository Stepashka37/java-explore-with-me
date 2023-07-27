package ru.dimax.main.model.dtos.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import ru.dimax.main.model.dtos.user.UserShortDto;
import ru.dimax.main.model.dtos.category.CategoryDto;

import java.time.LocalDateTime;

@Builder
@Data
public class EventShortDto {

    private String annotation;

    private CategoryDto category;

    public Long confirmedRequests;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    private Long id;

    private UserShortDto initiator;

    private Boolean paid;

    private String title;

    private Integer views;
}

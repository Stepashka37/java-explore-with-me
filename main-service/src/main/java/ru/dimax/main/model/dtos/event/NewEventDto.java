package ru.dimax.main.model.dtos.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.dimax.main.Constants.Constants;
import ru.dimax.main.model.Location;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewEventDto {

    @Size(max = 2000, min = 20)
    @NotBlank
    private String annotation;

    @Positive
    @NotNull
    private Long category;

    @Size(max = 7000, min = 20)
    @NotBlank
    private String description;

    @JsonFormat(pattern = Constants.DATE_TIME_PATTERN)
    @NotNull
    private LocalDateTime eventDate;

    @NotNull
    private Location location;

    @Builder.Default
    @NotNull
    private Boolean paid = false;

    @Builder.Default
    @NotNull
    private Long participantLimit = 0L;

    @Builder.Default
    @NotNull
    private Boolean requestModeration = true;

    @Size(max = 120, min = 3)
    @NotBlank
    private String title;



}

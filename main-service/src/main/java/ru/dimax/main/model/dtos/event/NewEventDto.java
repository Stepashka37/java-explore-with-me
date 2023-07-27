package ru.dimax.main.model.dtos.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.format.annotation.DateTimeFormat;
import ru.dimax.main.model.Location;

import javax.validation.constraints.*;
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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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

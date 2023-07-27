package ru.dimax.main.model.dtos.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.dimax.main.model.Location;
import ru.dimax.main.model.StateAction;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEventUserRequest {

    @Size(max = 2000, min = 20)
    //@NotBlank
    private String annotation;

    @Positive
    //@NotNull
    private Long category;

    @Size(max = 7000, min = 20)
    //@NotNull
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //@NotNull
    private LocalDateTime eventDate;

    //@NotNull
    private Location location;

    //@NotNull
    private Boolean paid;

    //@NotNull
    private Long participantLimit;

    //@NotNull
    private Boolean requestModeration;

    //@NotNull
    private StateAction stateAction;

    @Size(max = 120, min = 3)
    //@NotBlank
    private String title;
}

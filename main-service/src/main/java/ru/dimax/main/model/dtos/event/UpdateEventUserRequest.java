package ru.dimax.main.model.dtos.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.dimax.main.constants.Constants;
import ru.dimax.main.model.Location;
import ru.dimax.main.model.StateAction;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEventUserRequest {

    @Size(max = 2000, min = 20)
    private String annotation;

    @Positive
    private Long category;

    @Size(max = 7000, min = 20)
    private String description;

    @JsonFormat(pattern = Constants.DATE_TIME_PATTERN)
    private LocalDateTime eventDate;

    private Location location;

    private Boolean paid;

    private Long participantLimit;

    private Boolean requestModeration;

    private StateAction stateAction;

    @Size(max = 120, min = 3)
    private String title;
}

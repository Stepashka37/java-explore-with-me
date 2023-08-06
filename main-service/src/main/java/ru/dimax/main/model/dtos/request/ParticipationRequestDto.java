package ru.dimax.main.model.dtos.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import ru.dimax.main.constants.Constants;

import java.time.LocalDateTime;

@Data
@Builder

public class ParticipationRequestDto {

    @JsonFormat(pattern = Constants.DATE_TIME_PATTERN)
    private LocalDateTime created;


    private Long event;

    private Long id;

    private Long requester;

    private String status;
}

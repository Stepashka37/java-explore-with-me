package ru.dimax.main.model.dtos.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import ru.dimax.main.model.State;

import java.time.LocalDateTime;

@Data
@Builder

public class ParticipationRequestDto {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;


    private Long event;

    private Long id;

    private Long requester;

    private State status;
}
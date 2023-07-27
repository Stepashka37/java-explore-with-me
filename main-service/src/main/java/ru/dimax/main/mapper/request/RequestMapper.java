package ru.dimax.main.mapper.request;

import lombok.experimental.UtilityClass;
import ru.dimax.main.model.Request;
import ru.dimax.main.model.dtos.request.ParticipationRequestDto;

@UtilityClass
public class RequestMapper {

    public ParticipationRequestDto modelToDto(Request request) {
        return ParticipationRequestDto.builder()
                .id(request.getId())
                .event(request.getEvent().getId())
                .created(request.getCreated())
                .requester(request.getRequester().getId())
                .status(request.getStatus())
                .build();
    }
}

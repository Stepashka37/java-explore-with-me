package ru.dimax.main.service.request;

import ru.dimax.main.model.dtos.request.ConfirmedAndRejectedRequests;
import ru.dimax.main.model.dtos.request.EventRequestStatusUpdateRequest;
import ru.dimax.main.model.dtos.request.ParticipationRequestDto;

import java.util.List;

public interface RequestService {

    List<ParticipationRequestDto> getUserRequests(Long userId);

    ParticipationRequestDto createRequest(Long userId, Long eventId);

    ParticipationRequestDto cancelRequest(Long userId, Long requestId);

    List<ParticipationRequestDto> getRequestForCurrentEvent(Long userId, Long eventId);

    ConfirmedAndRejectedRequests changeRequestsStatus(Long userId, Long eventId, EventRequestStatusUpdateRequest request);


}

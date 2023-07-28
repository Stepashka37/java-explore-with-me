package ru.dimax.main.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dimax.main.model.dtos.event.EventFullDto;
import ru.dimax.main.model.dtos.event.EventShortDto;
import ru.dimax.main.model.dtos.event.NewEventDto;
import ru.dimax.main.model.dtos.event.UpdateEventUserRequest;
import ru.dimax.main.model.dtos.request.ConfirmedAndRejectedRequests;
import ru.dimax.main.model.dtos.request.EventRequestStatusUpdateRequest;
import ru.dimax.main.model.dtos.request.ParticipationRequestDto;
import ru.dimax.main.service.event.EventService;
import ru.dimax.main.service.request.RequestService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class PrivateController {

    private final EventService eventService;
    private final RequestService requestService;

    public PrivateController(EventService eventService, RequestService requestService) {
        this.eventService = eventService;
        this.requestService = requestService;
    }

    @PostMapping("/{userId}/events")
    public ResponseEntity<EventFullDto> createEvent(@PathVariable Long userId, @Valid @RequestBody NewEventDto newEventDto) {
        EventFullDto created = eventService.createEvent(userId, newEventDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{userId}/events")
    public ResponseEntity<List<EventShortDto>> getEventsAddedByUser(@PathVariable("userId") Long userId,
                                                                    @RequestParam(defaultValue = "0") Integer from,
                                                                    @RequestParam(defaultValue = "10") Integer size) {
        List<EventShortDto> events = eventService.getEventsAddedByUser(userId, from, size);
        return ResponseEntity.status(HttpStatus.OK).body(events);
    }

    @GetMapping("/{userId}/events/{eventId}")
    public ResponseEntity<EventFullDto> getEventAddedByCurrentUser(@PathVariable("userId") Long userId,
                                                                   @PathVariable("eventId") Long eventID) {
        EventFullDto eventFullDto = eventService.getEventAddedByCurrentUser(userId, eventID);
        return ResponseEntity.status(HttpStatus.OK).body(eventFullDto);
    }

    @PatchMapping("/{userId}/events/{eventId}")
    public ResponseEntity<EventFullDto> updateEventAddedByCurrentUser(@PathVariable("userId") Long userId,
                                                                      @PathVariable("eventId") Long eventID,
                                                                      @Valid @RequestBody UpdateEventUserRequest request) {
        EventFullDto eventFullDto = eventService.updateEventAddedByCurrentUser(userId, eventID, request);
        return ResponseEntity.status(HttpStatus.OK).body(eventFullDto);
    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    public ResponseEntity<List<ParticipationRequestDto>> getRequestForCurrentEvent(@PathVariable("userId") Long userId,
                                                                                   @PathVariable("eventId") Long eventId) {
        List<ParticipationRequestDto> requests = requestService.getRequestForCurrentEvent(userId, eventId);
        return ResponseEntity.status(HttpStatus.OK).body(requests);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests")
    public ResponseEntity<ConfirmedAndRejectedRequests> changeRequestStatus(@PathVariable("userId") Long userId,
                                                                            @PathVariable("eventId") Long eventId,
                                                                            @RequestBody(required = false) EventRequestStatusUpdateRequest requestsIds) {
        ConfirmedAndRejectedRequests requests = requestService.changeRequestsStatus(userId, eventId, requestsIds);
        return ResponseEntity.status(HttpStatus.OK).body(requests);
    }

    @GetMapping("/{userId}/requests")
    public ResponseEntity<List<ParticipationRequestDto>> getUserRequests(@PathVariable Long userId) {
        List<ParticipationRequestDto> requests = requestService.getUserRequests(userId);
        return ResponseEntity.status(HttpStatus.OK).body(requests);
    }

    @PostMapping("/{userId}/requests")
    public ResponseEntity<ParticipationRequestDto> createRequest(@PathVariable Long userId,
                                                                  @RequestParam(required = true) String eventId) {
        Long eventIdLong;
        try {
            eventIdLong = Long.parseLong(eventId);
        } catch (NumberFormatException e) {
            return  ResponseEntity.badRequest().build();
        }

        ParticipationRequestDto requestDto = requestService.createRequest(userId, eventIdLong);
        return ResponseEntity.status(HttpStatus.CREATED).body(requestDto);
    }

    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    public ResponseEntity<ParticipationRequestDto> cancelRequest(@PathVariable Long userId,
                                                                  @PathVariable Long requestId) {

        ParticipationRequestDto requestDto = requestService.cancelRequest(userId, requestId);
        return ResponseEntity.status(HttpStatus.OK).body(requestDto);
    }





}

package ru.dimax.main.service.event;

import ru.dimax.main.model.dtos.event.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface EventService {

    EventFullDto createEvent(Long userId, NewEventDto newEventDto);

    List<EventShortDto> getEventsAddedByUser(Long id, Integer from, Integer size);

    EventFullDto getEventAddedByCurrentUser(Long userId, Long eventId);

    EventFullDto updateEventAddedByCurrentUser(Long userId, Long eventId, UpdateEventUserRequest request);

    EventFullDto getPublishedEventById(Long id, HttpServletRequest response);

    List<EventShortDto> getPublishedEventsByFilters(String text,
                                                    List<Long> categoriesIds,
                                                    Boolean paid,
                                                    LocalDateTime rangeStart,
                                                    LocalDateTime rangeEnd,
                                                    Boolean onlyAvailable,
                                                    String sort,
                                                    Integer from,
                                                    Integer size,
                                                    HttpServletRequest response);

    List<EventFullDto> searchEvents(List<Long> userIds,
                                    List<String> states,
                                    List<Long> categoriesIds,
                                    LocalDateTime rangeStart,
                                    LocalDateTime rangeEnd,
                                    Integer from,
                                    Integer size);

    EventFullDto updateEventByAdmin(Long id, UpdateEventAdminRequest request);

    List<EventFullDto> searchEventsInLocation(Long locationId);
}

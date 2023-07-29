package ru.dimax.main.mapper.event;

import lombok.experimental.UtilityClass;
import ru.dimax.main.model.Category;
import ru.dimax.main.model.Event;
import ru.dimax.main.model.Request;
import ru.dimax.main.model.State;
import ru.dimax.main.model.dtos.event.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static ru.dimax.main.mapper.category.CategoryMapper.*;
import static ru.dimax.main.mapper.user.UserMapper.*;

@UtilityClass
public class EventMapper {

    public Event newEventToEvent(NewEventDto newEventDto) {
        return Event.builder()
                .id(0L)
                .category(new Category(newEventDto.getCategory()))
                .created(LocalDateTime.now())
                .annotation(newEventDto.getAnnotation())
                .requests(new ArrayList<>())
                .description(newEventDto.getDescription())
                .eventDate(newEventDto.getEventDate())
                .location(newEventDto.getLocation())
                .paid(newEventDto.getPaid())
                .participantLimit(newEventDto.getParticipantLimit())
                .requestModeration(newEventDto.getRequestModeration())
                .state(State.PENDING)
                .title(newEventDto.getTitle())
                .views(0)
                .build();
    }

    public EventFullDto eventToFullDto(Event event) {
        return EventFullDto.builder()
                .annotation(event.getAnnotation())
                .category(modelToDto(event.getCategory()))
                .confirmedRequests(event.getRequests()
                        .stream()
                        .filter(x -> x.getStatus().equals(Request.RequestState.CONFIRMED))
                        .count())
                .createdOn(event.getCreated())
                .description(event.getDescription())
                .eventDate(event.getEventDate())
                .id(event.getId())
                .initiator(modelToShortDto(event.getInitiator()))
                .location(event.getLocation())
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(event.getPublished())
                .requestModeration(event.getRequestModeration())
                .state(event.getState())
                .title(event.getTitle())
                .views(event.getViews())
                        .build();
    }

    public EventShortDto eventToShortDto(Event event) {
        return EventShortDto.builder()
                .annotation(event.getAnnotation())
                .category(modelToDto(event.getCategory()))
                .confirmedRequests(event.getRequests()
                        .stream()
                        .filter(x -> x.getStatus().equals(Request.RequestState.CONFIRMED))
                        .count())
                .eventDate(event.getEventDate())
                .id(event.getId())
                .initiator(modelToShortDto(event.getInitiator()))
                .paid(event.getPaid())
                .title(event.getTitle())
                .views(event.getViews())
                .build();
    }

    public Event updateUserRequestToEvent(UpdateEventUserRequest request) {
        return Event.builder()
                .id(0L)
                .category(new Category(request.getCategory()))
                .annotation(request.getAnnotation())
                .description(request.getDescription())
                .eventDate(request.getEventDate())
                .location(request.getLocation())
                .paid(request.getPaid())
                .participantLimit(request.getParticipantLimit())
                .requestModeration(request.getRequestModeration())
                .title(request.getTitle())
                .build();
    }

    public Event updateAdminRequestToEvent(UpdateEventAdminRequest request) {
        return Event.builder()
                .annotation(request.getAnnotation())
                .description(request.getDescription())
                .eventDate(request.getEventDate())
                .location(request.getLocation())
                .paid(request.getPaid())
                .participantLimit(request.getParticipantLimit())
                .requestModeration(request.getRequestModeration())
                .title(request.getTitle())
                .build();
    }
}

package ru.dimax.main.service.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.dimax.main.exception.AlreadyPublishedException;
import ru.dimax.main.exception.ConditionException;
import ru.dimax.main.exception.EntityNotFoundException;
import ru.dimax.main.exception.EntityValidationException;
import ru.dimax.main.mapper.event.EventUpdateMapper;
import ru.dimax.main.model.*;
import ru.dimax.main.model.dtos.event.*;
import ru.dimax.main.repository.EventRepository;
import ru.dimax.main.repository.UserRepository;
import ru.dimax.stats.client.StatsClient;
import ru.dimax.stats.dto.EndpointHit;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static ru.dimax.main.mapper.event.EventMapper.*;

@Service
@Slf4j
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private StatsClient statsClient = new StatsClient();

    public EventServiceImpl(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    @Override
    public EventFullDto createEvent(Long userId, NewEventDto newEventDto) {
        if (newEventDto.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new EntityValidationException("For the requested operation the conditions are not met.");
        }
        Event event = newEventToEvent(newEventDto);
        User initiator = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(String.format("User %s not found", userId)));
        event.setInitiator(initiator);
        Event eventCreated = eventRepository.save(event);
        log.info("Created event with id: %s", event.getId());
        return eventToFullDto(eventCreated);
    }

    @Override
    public List<EventShortDto> getEventsAddedByUser(Long userId, Integer from, Integer size) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User %s not found", userId)));
        int page = from / size;
        Pageable pageable = PageRequest.of(page, size);
        List<Event> events = new ArrayList<>();
        events = eventRepository.findAllByInitiatorId(userId, pageable);
        log.info("Got events added by user with id:", userId);
        return events.stream()
                .map(x -> eventToShortDto(x))
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto getEventAddedByCurrentUser(Long userId, Long eventId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User %s not found", userId)));

        Event event = eventRepository.findByIdAndInitiatorId(eventId, userId);

        if (event == null) {
            throw new EntityNotFoundException(String.format("Event %s not found", eventId));
        }
        log.info("Got event with id: %s added by user with id: %s", eventId, userId);

        return eventToFullDto(event);
    }

    @Override
    public EventFullDto updateEventAddedByCurrentUser(Long userId, Long eventId, UpdateEventUserRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User %s not found", userId)));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Event %s not found", eventId)));

        if (request.getEventDate() != null && request.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new EntityValidationException("New event date cannot be earlier than current time + 2 hours");
        }

        if (event.getState().equals(State.CANCELED) || event.getState().equals(State.PENDING)
        || event.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            EventUpdateMapper.INSTANCE.updateEventFromDtoByUser(request, event);

            if (request.getStateAction() != null) {

                if (request.getStateAction().equals(StateAction.SEND_TO_REVIEW)) {
                    event.setState(State.PENDING);
                } else if (request.getStateAction().equals(StateAction.CANCEL_REVIEW)) {
                    event.setState(State.CANCELED);
                }

            }

            Event eventUPD = eventRepository.save(event);
            log.info("Updated event with id: %s added by user with id: %s", eventId, userId);
            return eventToFullDto(eventUPD);
        }
        throw new AlreadyPublishedException("Published event cannot be changed.");
    }

    @Override

    public EventFullDto getPublishedEventById(Long eventId, HttpServletRequest request) {
        statsClient.hit(EndpointHit.builder()
                .ip(request.getRemoteAddr())
                .uri(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .app("ewm-main-service")
                .build());

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Event %s not found", eventId)));

        Integer views = getEventViews(event);

        event.setViews(views);

        if (!event.getState().equals(State.PUBLISHED)) {
            throw new EntityNotFoundException(String.format("Event %s not found", eventId));
        }

        Event eventUPD = eventRepository.saveAndFlush(event);
        log.info("Got published event with id: %s", eventId);
        return eventToFullDto(eventUPD);
    }

    @Override
    public List<EventShortDto> getPublishedEventsByFilters(String text,
                                                           List<Long> categoriesIds,
                                                           Boolean paid,
                                                           LocalDateTime rangeStart,
                                                           LocalDateTime rangeEnd,
                                                           Boolean onlyAvailable,
                                                           String sort, Integer from,
                                                           Integer size,
                                                           HttpServletRequest request) {
        int page = from / size;
        Pageable pageable = PageRequest.of(page, size);

        Sort sort1  = Sort.valueOf(sort);

        if (rangeStart.isAfter(rangeEnd)) {
            throw new EntityValidationException("End time cannot be earlier than start time");
        }

        statsClient.hit(EndpointHit.builder()
                .ip(request.getRemoteAddr())
                .uri(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .app("ewm-main-service")
                .build());

        List<Event> events = new ArrayList<>();
        if (sort1.equals(Sort.VIEWS)) {
            events = eventRepository.findEventsByFiltersSortByViews(text, categoriesIds, paid, rangeStart, rangeEnd, onlyAvailable, pageable);
        } else {
            events = eventRepository.findEventsByFiltersSortByEventDate(text, categoriesIds, paid, rangeStart, rangeEnd, onlyAvailable, pageable);
        }
        log.info("Got published events using filters");
        return events.stream()
                .map(x -> eventToShortDto(x))
                .collect(Collectors.toList());
    }

    @Override
    public List<EventFullDto> searchEvents(List<Long> userIds, List<String> states, List<Long> categoriesIds, LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size) {
       int page = from / size;
        Pageable pageable = PageRequest.of(page, size);
        List<State> validStates = null;

        if (states != null) {
            validStates = states.stream()
                    .map(x -> State.valueOf(x))
                    .collect(Collectors.toList());
        }

        List<Event> events = eventRepository.searchEvents(userIds, validStates, categoriesIds, rangeStart, rangeEnd, pageable);
        log.info("Found events using filters");
        return events.stream()
                .map(x -> eventToFullDto(x))
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto updateEventByAdmin(Long eventId, UpdateEventAdminRequest request) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Event %s not found", eventId)));

        if (event.getState().equals(State.PUBLISHED) || event.getState().equals(State.CANCELED)) {
            throw new AlreadyPublishedException("This event is already published or cancelled.");
        }

        if (event.getPublished() != null && !event.getEventDate().isAfter(event.getPublished().plusHours(1))) {
            throw new ConditionException("For the requested operation the conditions are not met.");
        }

        if (request.getEventDate() != null && request.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new EntityValidationException("New event date cannot be earlier than current time + 2 hours");
        }

        if (request.getStateAction() != null) {
            if (event.getState().equals(State.PENDING) && request.getStateAction().equals(StateAction.PUBLISH_EVENT)) {
                event.setState(State.PUBLISHED);
                event.setPublished(LocalDateTime.now());
            } else if (request.getStateAction().equals(StateAction.REJECT_EVENT) && !event.getState().equals(State.PUBLISHED)) {
                event.setState(State.CANCELED);
            }
        }
        EventUpdateMapper.INSTANCE.updateEventFromDtoByAdmin(request, event);

        Event eventUPD = eventRepository.save(event);
        log.info("Updated event with id: %s by admin", eventId);
        return eventToFullDto(eventUPD);
    }


    private Integer getEventViews(Event event) {

        ResponseEntity<Object> objectResponseEntity = statsClient.getStat(event.getCreated().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                event.getEventDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                List.of("/events/" + event.getId()),
                true);

        String bodyToString = objectResponseEntity.getBody().toString();
        String regex = "hits=(\\d+)";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(bodyToString);

        int views = 0;
        if (matcher.find()) {
            String hitsValue = matcher.group(1);
            views = Integer.parseInt(hitsValue);
        }
        return views;
    }


}

package ru.dimax.main.service.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dimax.main.exception.ConditionException;
import ru.dimax.main.exception.ConflictException;
import ru.dimax.main.exception.EntityNotFoundException;
import ru.dimax.main.exception.RequestApplicationException;
import ru.dimax.main.model.Event;
import ru.dimax.main.model.Request;
import ru.dimax.main.model.State;
import ru.dimax.main.model.User;
import ru.dimax.main.model.dtos.request.ConfirmedAndRejectedRequests;
import ru.dimax.main.model.dtos.request.EventRequestStatusUpdateRequest;
import ru.dimax.main.model.dtos.request.ParticipationRequestDto;
import ru.dimax.main.repository.EventRepository;
import ru.dimax.main.repository.RequestRepository;
import ru.dimax.main.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.dimax.main.mapper.request.RequestMapper.*;

@Service
@Slf4j
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public RequestServiceImpl(RequestRepository requestRepository, EventRepository eventRepository, UserRepository userRepository) {
        this.requestRepository = requestRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<ParticipationRequestDto> getUserRequests(Long userId) {
        List<Request> requests = requestRepository.findAllByRequesterId(userId);
        if (requests == null) requests = new ArrayList<>();
        log.info("Got requests of user with id: %s", userId);
        return requests.stream()
                .map(x -> modelToDto(x))
                .collect(Collectors.toList());
    }

    @Override
    public ParticipationRequestDto createRequest(Long userId, Long eventId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User %s not found", userId)));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Event %s not found", eventId)));

        Request request = requestRepository.findByRequesterIdAndEventId(userId, eventId);

        if (request != null) {
            throw new RequestApplicationException("Request already exists");
        }

        if (!event.getState().equals(State.PUBLISHED)) {
            throw new RequestApplicationException("The event is not published");
        }

        if (event.getRequests().stream()
                .map(Request::getRequester)
                .anyMatch(requester -> requester.equals(user))) {
            throw new RequestApplicationException("The user has already sent the request");
        }

        if (event.getInitiator().equals(user)) {
            throw new RequestApplicationException("Initiator cannot participate its event");
        }

        if (!event.getParticipantLimit().equals(0L)) {
            List<Request> confirmedRequest = event.getRequests().stream()
                    .filter(r -> r.getStatus().equals(Request.RequestState.CONFIRMED))
                    .collect(Collectors.toList());

            if (confirmedRequest.size() == event.getParticipantLimit()) {
                throw new RequestApplicationException("Participants limit is reached");
            }
        }

        Request requestToSave = Request.builder()
                .created(LocalDateTime.now())
                .requester(user)
                .event(event)
                .status(Request.RequestState.PENDING)
                .build();

        if (!event.getRequestModeration() || event.getParticipantLimit().equals(0L)) {
            requestToSave.setStatus(Request.RequestState.CONFIRMED);
        }

        Request requestSaved = requestRepository.saveAndFlush(requestToSave);
        log.info("Created request for event with id: %s of user with id: %s", eventId, userId);
        return modelToDto(requestSaved);

    }

    @Override
    @Transactional
    public ParticipationRequestDto cancelRequest(Long userId, Long requestId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User %s not found", userId)));

        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Request %s not found", requestId)));
        request.setStatus(Request.RequestState.CANCELED);


        Request requestUPD = requestRepository.saveAndFlush(request);
        log.info("Cancel request  with id: %s of user with id: %s", requestId, userId);
        return modelToDto(requestUPD);
    }

    @Override
    public List<ParticipationRequestDto> getRequestForCurrentEvent(Long userId, Long eventId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User %s not found", userId)));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Event %s not found", eventId)));

        List<Request> requests = requestRepository.findAllByEventId(eventId);
        log.info("Got requests for event with id: %s ", eventId);
        return requests.stream()
                .map(x -> modelToDto(x))
                .collect(Collectors.toList());
    }

    @Override
    public ConfirmedAndRejectedRequests changeRequestsStatus(Long userId, Long eventId, EventRequestStatusUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User %s not found", userId)));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Event %s not found", eventId)));

        Long confirmedRequest = event.getRequests().stream()
                .filter(r -> r.getStatus().equals(Request.RequestState.CONFIRMED))
                .count();

        if (confirmedRequest.equals(event.getParticipantLimit())) {
            throw new ConflictException("Participant limit exceeded");
        }

        if (event.getParticipantLimit() == 0 || event.getRequestModeration() == false) {
            return new ConfirmedAndRejectedRequests();
        }

        List<Request> requests = requestRepository.findAllById(request.getRequestIds())
                .stream()
                .collect(Collectors.toList());

        Long participantLimit = event.getParticipantLimit();
        List<Request> confirmedRequests = new ArrayList<>();
        List<Request> rejectedRequests = new ArrayList<>();

            for (Request requestToConfirm : requests) {
                if (requestToConfirm.getStatus().equals(Request.RequestState.PENDING)) {
                   requestToConfirm.setStatus(request.getStatus().equals(
                           Request.RequestState.CONFIRMED) ? Request.RequestState.CONFIRMED : Request.RequestState.REJECTED
                   );
                if (requestToConfirm.getStatus().equals(Request.RequestState.CONFIRMED)) {
                    confirmedRequests.add(requestToConfirm);
                } else {
                    rejectedRequests.add(requestToConfirm);
                }
            } else {
                    throw  new ConditionException(String.format("Request {} must be pending", requestToConfirm.getId()));
                }

                if (confirmedRequests.size() == event.getParticipantLimit()) {
                    for (Request leftRequest : requests) {
                        if (leftRequest.getStatus().equals(Request.RequestState.PENDING)) {
                        leftRequest.setStatus(Request.RequestState.REJECTED);
                        rejectedRequests.add(leftRequest);
                        }
                    }
                    }
                }

        Event eventUPD = eventRepository.saveAndFlush(event);
        List<ParticipationRequestDto> confirmedDto = confirmedRequests.stream()
                .map(x -> modelToDto(x))
        .collect(Collectors.toList());

        List<ParticipationRequestDto> rejectedDto = rejectedRequests.stream()
                .map(x -> modelToDto(x))
                .collect(Collectors.toList());

        log.info("Changed status for requests for event with id: %s ", eventId);
        return new ConfirmedAndRejectedRequests(confirmedDto, rejectedDto);

    }

    private List<List<Request>> saveConfirmedAndRejected(List<Request> confirmedRequests, List<Request> rejectedRequests) {
        requestRepository.deleteAllById(confirmedRequests.stream().map(x -> x.getId()).collect(Collectors.toList()));
        List<Request> confirmed = requestRepository.saveAll(confirmedRequests);

        requestRepository.deleteAllById(rejectedRequests.stream().map(x -> x.getId()).collect(Collectors.toList()));
        rejectedRequests.stream().forEach(r -> r.setStatus(Request.RequestState.REJECTED));
        List<Request> rejected = requestRepository.saveAll(rejectedRequests);
        return List.of(confirmed, rejected);
    }
}

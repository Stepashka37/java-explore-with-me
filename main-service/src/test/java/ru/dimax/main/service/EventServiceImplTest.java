/*

package ru.dimax.main.service;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import ru.dimax.main.EwmMainService;
import ru.dimax.main.model.*;
import ru.dimax.main.model.dtos.event.EventFullDto;
import ru.dimax.main.model.dtos.event.UpdateEventUserRequest;
import ru.dimax.main.repository.EventRepository;
import ru.dimax.main.repository.UserRepository;
import ru.dimax.main.service.event.EventServiceImpl;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest(classes = EwmMainService.class)
@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private UserRepository userRepository;

    private Faker faker = new Faker();

    @InjectMocks
    private EventServiceImpl underTest;

    @BeforeEach
    void setUp() {
        underTest = new EventServiceImpl(eventRepository, userRepository, categoryRepository, statsClient);
    }

    @Test
    void itShouldUpdateEvent() {
        // Given
        UpdateEventUserRequest request = UpdateEventUserRequest.builder()
                .annotation("random annotatiton textwwwwwwwwww")
                .category(1L)
                .description("random annotatiton textwwwwwwwwww")
                .eventDate(LocalDateTime.now())
                .location(new Location(55.333f, 55.333f))
                .paid(true)
                .participantLimit(15)
                .requestModeration(true)
                .stateAction("CANCELLED")
                .title("Title")
                .build();

        User user = User.builder()
                .id(1L)
                .name(faker.name().fullName())
                .email(String.format("%s@practicum.ru", faker.name().firstName()))
                .build();

        Event event = Event.builder()
                .id(1L)
                .annotation("before UPD fddsgfdgdfg")
                .category(new Category(1L, "Music"))
                .created(LocalDateTime.now().minusMinutes(10))
                .description("before UPD bgfbfbfbfg")
                .eventDate(LocalDateTime.now())
                .confirmedRequests(5)
                .initiator(user)
                .location(new Location(55.222f, 55.222f))
                .paid(false)
                .participantLimit(5)
                .published(LocalDateTime.now().minusMinutes(5))
                .requestModeration(false)
                .state(State.PUBLISHED)
                .title("Title before UPD")
                .views(5)
                .build();

        Event eventUPD = Event.builder()
                .id(1L)
                .annotation("random annotatiton textwwwwwwwwww")
                .category(new Category(1L, "Music"))
                .created(LocalDateTime.now().minusMinutes(10))
                .description("random annotatiton textwwwwwwwwww")
                .eventDate(LocalDateTime.now())
                .confirmedRequests(5)
                .initiator(user)
                .location(new Location(55.333f, 55.333f))
                .paid(true)
                .participantLimit(5)
                .published(LocalDateTime.now().minusMinutes(5))
                .requestModeration(true)
                .state(State.PUBLISHED)
                .title("Title")
                .views(5)
                .build();

        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        when(eventRepository.findById(any(Long.class))).thenReturn(Optional.of(event));
        when(eventRepository.save(any(Event.class))).thenReturn(eventUPD);

        // When
        EventFullDto eventUPDATED = underTest.updateEventAddedByCurrentUser(1L, 1L, request);
        // Then
        System.out.println(eventUPDATED);
    }
}
*/

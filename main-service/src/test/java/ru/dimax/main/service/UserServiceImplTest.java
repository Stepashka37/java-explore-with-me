package ru.dimax.main.service;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.dimax.main.EwmMainService;
import ru.dimax.main.model.User;
import ru.dimax.main.model.dtos.NewUserRequest;
import ru.dimax.main.model.dtos.UserDto;
import ru.dimax.main.repository.UserRepository;
import ru.dimax.main.service.UserService;
import ru.dimax.main.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static ru.dimax.main.mapper.UserMapper.*;

@SpringBootTest(classes = EwmMainService.class)
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    private Faker faker = new Faker();

    @InjectMocks
    private UserServiceImpl underTest;

    @BeforeEach
    void setUp() {
        underTest = new UserServiceImpl(userRepository);
    }

    @Test
    void itShouldSaveUser() {
        // Given
        NewUserRequest dto = NewUserRequest.builder()
                .name(faker.name().fullName())
                .email(String.format("%s@practicum.ru", faker.funnyName()))
                .build();
        // When
        when(userRepository.save(any(User.class)))
                .thenReturn(requestDtoToModel(dto));
        UserDto actualUser = underTest.createUser(dto);
        // Then
        assertThat(actualUser).isEqualToIgnoringGivenFields(dto, "id");
        verify(userRepository, times(1)).save(any());

    }

    @Test
    void itShouldGetAllUsersWhenIdsEmpty() {
        // Given
        User user = User.builder()
                .id(1L)
                .name(faker.name().fullName())
                .email(String.format("%s@practicum.ru", faker.name().firstName()))
                .build();

        User user1 = User.builder()
                .id(2L)
                .name(faker.name().fullName())
                .email(String.format("%s@practicum.ru", faker.name().firstName()))
                .build();

        User user2 = User.builder()
                .id(3L)
                .name(faker.name().fullName())
                .email(String.format("%s@practicum.ru", faker.name().firstName()))
                .build();
        Pageable pageable = PageRequest.of(0, 3);
        Page<User> page = new PageImpl<>(List.of(user, user2, user1), pageable, 3);
        when(userRepository.findAll(pageable)).thenReturn(page);
        // When
        List<UserDto> users = underTest.getUsers(new ArrayList<>(), 0, 3 );

        // Then
        assertThat(users.size()).isEqualTo(3);
    }

    @Test
    void itShouldGetAllUsersInIds() {
        // Given
        User user = User.builder()
                .id(1L)
                .name(faker.name().fullName())
                .email(String.format("%s@practicum.ru", faker.name().firstName()))
                .build();

        User user1 = User.builder()
                .id(2L)
                .name(faker.name().fullName())
                .email(String.format("%s@practicum.ru", faker.name().firstName()))
                .build();

        User user2 = User.builder()
                .id(3L)
                .name(faker.name().fullName())
                .email(String.format("%s@practicum.ru", faker.name().firstName()))
                .build();

        List<Long> ids = List.of(1L, 2L);
        Pageable pageable = PageRequest.of(0, 2);
        when(userRepository.findAllByIdIn(ids, pageable)).thenReturn(List.of(user, user1));
        // When
        List<UserDto> users = underTest.getUsers(ids, 0, 2 );

        // Then
        assertThat(users.size()).isEqualTo(2);
    }

    @Test
    void itShouldDeleteUser() {
        // Given
        User user = User.builder()
                .id(1L)
                .name(faker.name().fullName())
                .email(String.format("%s@practicum.ru", faker.name().firstName()))
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        // When
        underTest.deleteUser(1L);
        // Then
        verify(userRepository, times(1)).deleteById(1L);
    }
}
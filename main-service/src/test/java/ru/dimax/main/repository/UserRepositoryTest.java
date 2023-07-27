package ru.dimax.main.repository;


import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.dimax.main.EwmMainService;
import ru.dimax.main.model.User;
import ru.dimax.main.repository.UserRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles(profiles = {"ci,test"})
@DataJpaTest(
        properties = {
                "spring.jpa.properties.javax.persistence.validation.mode=none"
        }
)
//@SpringBootTest(classes = EwmMainService.class)

public class UserRepositoryTest {

    @Autowired
    private UserRepository underTest;

    private Faker faker = new Faker();

    @Test
    void itShouldBeInitialized() {
        // Given
        // When
        // Then
        assertThat(underTest).isNotNull();
    }

    @Test
    void itShouldGetUsers() {
        // Given
        User user = User.builder()
                .id(Long.valueOf(faker.number().randomNumber()))
                .name(faker.name().fullName())
                .email(String.format("%s@practicum.ru", faker.name().firstName()))
                .build();

        User user1 = User.builder()
                .id(Long.valueOf(faker.number().randomNumber()))
                .name(faker.name().fullName())
                .email(String.format("%s@practicum.ru", faker.name().firstName()))
                .build();

        User user2 = User.builder()
                .id(Long.valueOf(faker.number().randomNumber()))
                .name(faker.name().fullName())
                .email(String.format("%s@practicum.ru", faker.name().firstName()))
                .build();

        User userSaved = underTest.save(user);
        User user2Saved = underTest.save(user2);
        User user1Saved = underTest.save(user1);

        // When
        // Then
        Pageable pageable = PageRequest.of(0, 3);
        /*List<Long> ids = List.of(1L, 2L, 3L);
        List<User> allUsers = underTest.findAll();

        List<User> users = underTest.findAllByIdIn(ids, pageable);
        assertThat(users)
                .hasSize(3)
                .contains(userSaved, user1Saved, user2Saved);*/
    }


}

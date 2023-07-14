import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.dimax.stats.dto.EndpointHit;
import ru.dimax.stats.server.repository.dao.EndpointRepository;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {EndpointRepository.class})
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class StatRepositoryTest {


    private final EndpointRepository underTest;

    @Test
    void itShouldBeInitialized() {
        // Given
        // When
        // Then
        assertThat(underTest).isNotNull();

    }

    @Test
    void itShouldBeAbleToFindAll() {
        EndpointHit hit1 = EndpointHit.builder()
                .id(1L)
                .ip(String.valueOf("127.0.0."))
                .app("app")
                .uri("228")
                .timestamp(LocalDateTime.now())
                .build();

        EndpointHit hit2 = EndpointHit.builder()
                .id(2L)
                .ip(String.valueOf("128.0.0."))
                .app("app")
                .uri("229")
                .timestamp(LocalDateTime.now())
                .build();


        underTest.saveHit(hit2);

        System.out.println( underTest.saveHit(hit1));
        System.out.println( underTest.saveHit(hit2));
    }

}

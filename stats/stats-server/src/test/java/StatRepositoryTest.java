import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import ru.dimax.stats.dto.ViewStats;
import ru.dimax.stats.server.model.EndpointHit;
import ru.dimax.stats.server.repository.StatRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(
        properties = {
                "spring.jpa.properties.javax.persistence.validation.mode=none"
        }
)
@Transactional
public class StatRepositoryTest {
    @Autowired
    private StatRepository underTest;

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

        underTest.save(hit1);
        underTest.save(hit2);


    }

}

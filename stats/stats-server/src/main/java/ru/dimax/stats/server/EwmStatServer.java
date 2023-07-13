package ru.dimax.stats.server;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.dimax.stats.dto.ViewStats;
import ru.dimax.stats.server.model.EndpointHit;
import ru.dimax.stats.server.repository.StatRepository;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class EwmStatServer {
    public static void main(String[] args) {
        SpringApplication.run(EwmStatServer.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(
            StatRepository statRepository
    ) {
        return args -> {

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

            statRepository.save(hit1);
            statRepository.save(hit2);

            List<EndpointHit> result = statRepository.findByTimestampBetweenAndUriIn(LocalDateTime.now().minusMinutes(5),
                    LocalDateTime.now().plusMinutes(5),
                    new String[]{"228", "229"});
            System.out.println(result);
        };
    }
}


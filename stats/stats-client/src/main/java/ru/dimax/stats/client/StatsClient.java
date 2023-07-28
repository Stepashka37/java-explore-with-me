package ru.dimax.stats.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.lang.Nullable;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.dimax.stats.dto.EndpointHit;
import ru.dimax.stats.dto.ViewStats;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Slf4j
public class StatsClient {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private  String application;

    private  String statsServiceUri;

    private  ObjectMapper objectMapper;

    private  RestTemplate  restTemplate;

    public StatsClient() {
        this.application = "ewm-main-service";
        this.statsServiceUri = "http://stats-server:9090";
        this.objectMapper = new ObjectMapper();
        this.restTemplate = new RestTemplateBuilder()
                .uriTemplateHandler(new DefaultUriBuilderFactory(statsServiceUri))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build();
    }

    public StatsClient(@Value("ewm-main-service") String application,
                       @Value("http://stats-server:9090") String statsServiceUri,
                       ObjectMapper objectMapper, RestTemplate restTemplate) {
        this.application = application;
        this.statsServiceUri = statsServiceUri;
        this.objectMapper = objectMapper;
        this.restTemplate = new RestTemplateBuilder()
                .uriTemplateHandler(new DefaultUriBuilderFactory(statsServiceUri))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build();
    }

    public <T> ResponseEntity<Object>  hit(EndpointHit hit) {
      return makeAndSendRequest(HttpMethod.POST, "/hit", null, hit);
    }

    public <T> ResponseEntity<Object> getStat(String start, String end, List<String> uris, Boolean unique) {
        if (uris == null) {
            Map<String, Object> parameters = Map.of(
                    "start", start,
                    "end", end,
                    "unique", unique);
            return makeAndSendRequest(HttpMethod.GET, "/stats?start={start}&end={end}&unique={unique}", parameters, null);
        } else {
            Map<String, Object> parameters = Map.of(
                    "start", start,
                    "end", end,
                    "uris", uris,
                    "unique", unique);
            return makeAndSendRequest(HttpMethod.GET, "/stats?start={start}&end={end}&uris={uris}&unique={unique}", parameters, null);
        }

    }

    private <T> ResponseEntity<Object> makeAndSendRequest(HttpMethod method, String path, @Nullable Map<String, Object> parameters, @Nullable T body) {
        HttpEntity<T> requestEntity = new HttpEntity<>(body, defaultHeaders());
        ResponseEntity<Object> statServiceResponse;
        try {
            if (parameters != null) {
                statServiceResponse = restTemplate.exchange(path, method, requestEntity, Object.class, parameters);
            } else {
                statServiceResponse = restTemplate.exchange(path, method, requestEntity, Object.class);
            }
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsByteArray());
        }
        return prepareGatewayResponse(statServiceResponse);
    }

    private HttpHeaders defaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
    }

    private static ResponseEntity<Object> prepareGatewayResponse(ResponseEntity<Object> response) {
        if (response.getStatusCode().is2xxSuccessful()) {
            return response;
        }

        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(response.getStatusCode());

        if (response.hasBody()) {
            return responseBuilder.body(response.getBody());
        }

        return responseBuilder.build();
    }
}

package ru.dimax.stats.server.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@Entity(name = "endpoint_hit")
@Table(name = "endpoint_hits")
public class EndpointHit {

    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @Column(name = "id",
            updatable = false)
    private Long id;

    @Column(name = "app",
            nullable = false)
    private String app;
    @Column(name = "uri",
            nullable = false)
    private String uri;
    @Column(name = "ip",
            nullable = false)
    private String ip;
    @Column(name = "timestamp",
            nullable = false,
            columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private LocalDateTime timestamp;

    public EndpointHit() {

    }

    public EndpointHit(Long id, String app, String uri, String ip, LocalDateTime timestamp) {
        this.id = id;
        this.app = app;
        this.uri = uri;
        this.ip = ip;
        this.timestamp = timestamp;
    }
}

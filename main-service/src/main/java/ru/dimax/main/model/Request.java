package ru.dimax.main.model;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "requests")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created")
    private LocalDateTime created;

    @OneToOne
    @JoinColumn(name = "requester_id",
            referencedColumnName = "id")
    private User requester;

    @ManyToOne
    @JoinColumn(name = "event_id",
            referencedColumnName = "id")
    private Event event;

    @Column(name = "status")
    private State status;


}

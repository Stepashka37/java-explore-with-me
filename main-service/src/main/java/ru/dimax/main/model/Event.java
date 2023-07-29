package ru.dimax.main.model;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Table(name = "events")
@Entity
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "annotation", length = 2000)
    private String annotation;

    @ManyToOne
    @JoinColumn(name = "category_id",
    referencedColumnName = "id")
    private Category category;


    @Column(name = "created_on")
    private LocalDateTime created;

    @Column(name = "description", length = 7000)
    private String description;

    @Column(name = "event_date")
    private LocalDateTime eventDate;

    @OneToMany(mappedBy = "event")
    private List<Request> requests;

    @ManyToOne
    @JoinColumn(name = "initiator_id",
    referencedColumnName = "id")
    private User initiator;

    @Embedded
    private Location location;

    @Column(name = "paid")
    private Boolean paid;

    @Column(name = "participant_limit")
    private Long participantLimit;

    @Column(name = "published_on")
    private LocalDateTime published;

    @Column(name = "moderation")
    private Boolean requestModeration;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'PENDING'")
    private State state;

    @Column(name = "title", length = 120)
    private String title;


    private Integer views;

}

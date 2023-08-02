package ru.dimax.main.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "geo_location")
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GeoLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "latitude")
    private Float lat;

    @Column(name = "longitude")
    private Float lon;

    @OneToMany(mappedBy = "geoLocation")
    @OrderBy("eventDate ASC")
    private List<Event> events;

}

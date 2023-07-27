package ru.dimax.main.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "email", length = 254, unique = true)
    private String email;

    @Column(name = "name", length = 250)
    private String name;

    @OneToMany(mappedBy = "initiator")
    private List<Event> initiatedEvents;

}

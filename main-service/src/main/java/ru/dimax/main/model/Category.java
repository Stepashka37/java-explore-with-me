package ru.dimax.main.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Table(name = "categories")
@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 50, unique = true)
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Event> events;


    public Category(Long id) {
        this.id = id;
    }

}

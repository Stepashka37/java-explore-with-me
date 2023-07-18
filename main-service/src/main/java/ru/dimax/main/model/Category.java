package ru.dimax.main.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "categories")
@Entity
@Builder
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 20, unique = true)
    private String name;

    public Category() {
    }

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }


}

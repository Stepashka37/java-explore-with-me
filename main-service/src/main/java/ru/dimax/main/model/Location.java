package ru.dimax.main.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Location {

    @Column(name = "latitude")
    private Float lat;

    @Column(name = "longitude")
    private Float lon;
}

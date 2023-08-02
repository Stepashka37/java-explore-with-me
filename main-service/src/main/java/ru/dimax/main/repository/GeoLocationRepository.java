package ru.dimax.main.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.dimax.main.model.GeoLocation;


public interface GeoLocationRepository extends JpaRepository<GeoLocation, Long> {


}

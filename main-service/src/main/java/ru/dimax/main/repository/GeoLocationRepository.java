package ru.dimax.main.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.dimax.main.model.GeoLocation;

import java.util.List;

public interface GeoLocationRepository extends JpaRepository<GeoLocation, Long> {


}

package ru.dimax.main.service.geolocation;

import ru.dimax.main.model.dtos.geolocation.FullGeoLocationDto;
import ru.dimax.main.model.dtos.geolocation.NewGeoLocationDto;

import java.util.List;

public interface GeoLocationService {

    FullGeoLocationDto addGeoLocation(NewGeoLocationDto dto);

    List<FullGeoLocationDto> getAllLocations(Integer from, Integer size);
}

package ru.dimax.main.mapper.geolocation;

import lombok.experimental.UtilityClass;
import ru.dimax.main.model.GeoLocation;
import ru.dimax.main.model.dtos.geolocation.FullGeoLocationDto;
import ru.dimax.main.model.dtos.geolocation.NewGeoLocationDto;

import java.util.stream.Collectors;

import static ru.dimax.main.mapper.event.EventMapper.*;

@UtilityClass
public class GeoLocationMapper {

     public FullGeoLocationDto modelToDto(GeoLocation geoLocation) {
         return FullGeoLocationDto.builder()
                 .id(geoLocation.getId())
                 .lat(geoLocation.getLat())
                 .lon(geoLocation.getLon())
                 .name(geoLocation.getName())
                 .events(geoLocation.getEvents().stream()
                         .map(x -> eventToFullDto(x))
                         .collect(Collectors.toList()))
                 .build();
     }

     public GeoLocation dtoToModel(NewGeoLocationDto newGeoLocationDto) {
         return GeoLocation.builder()
                 .id(0L)
                 .lat(newGeoLocationDto.getLat())
                 .lon(newGeoLocationDto.getLon())
                 .name(newGeoLocationDto.getName())
                 .build();
     }

}

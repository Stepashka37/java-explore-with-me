package ru.dimax.main.service.geolocation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dimax.main.model.Event;
import ru.dimax.main.model.GeoLocation;
import ru.dimax.main.model.dtos.geolocation.FullGeoLocationDto;
import ru.dimax.main.model.dtos.geolocation.NewGeoLocationDto;
import ru.dimax.main.repository.EventRepository;
import ru.dimax.main.repository.GeoLocationRepository;

import java.util.List;
import java.util.stream.Collectors;

import static ru.dimax.main.mapper.geolocation.GeoLocationMapper.*;

@Service
@Slf4j
public class GeoLocationServiceImpl implements GeoLocationService {

    private final GeoLocationRepository geoLocationRepository;
    private final EventRepository eventRepository;

    public GeoLocationServiceImpl(GeoLocationRepository geoLocationRepository, EventRepository eventRepository) {
        this.geoLocationRepository = geoLocationRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    @Transactional
    public FullGeoLocationDto addGeoLocation(NewGeoLocationDto dto) {
        GeoLocation geoLocationToAdd = dtoToModel(dto);
        List<Event> eventsInLocation = eventRepository.findAllInLocation(geoLocationToAdd.getLat(), geoLocationToAdd.getLon());
        geoLocationToAdd.setEvents(eventsInLocation);
        GeoLocation geoLocationSaved = geoLocationRepository.save(geoLocationToAdd);
        log.info(String.format("Added geolocation with id: %s", geoLocationSaved.getId()));
        return modelToDto(geoLocationSaved);
    }

    @Override
    public List<FullGeoLocationDto> getAllLocations(Integer from, Integer size) {
        int page = from / size;
        Pageable pageable = PageRequest.of(page, size);
        List<GeoLocation> locations = geoLocationRepository.findAll(pageable).getContent();
        log.info("Get all locations");
        return locations.stream()
                .map(x -> modelToDto(x))
                .collect(Collectors.toList());
    }
}

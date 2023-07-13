package ru.dimax.stats.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dimax.stats.dto.EndpointHitDto;
import ru.dimax.stats.dto.ViewStats;
import ru.dimax.stats.server.mapper.EndpointHitMapper;
import ru.dimax.stats.server.model.EndpointHit;
import ru.dimax.stats.server.repository.StatRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.dimax.stats.server.mapper.EndpointHitMapper.*;

@Service
public class StatServiceImpl implements StatService {
    private final StatRepository statRepository;

    @Autowired
    public StatServiceImpl(StatRepository statRepository) {
        this.statRepository = statRepository;
    }

    @Override
    public EndpointHitDto saveHit(EndpointHitDto endpointHitDto) {
        EndpointHit model = dtoToModel(endpointHitDto);
        EndpointHit saved = statRepository.save(model);
        return modelToDto(saved);
    }

    @Override
    public List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, String[] uris, Boolean unique) {

    List<EndpointHit> viewStats = statRepository.findByTimestampBetweenAndUriIn(start, end, uris);
return null;
    }


}

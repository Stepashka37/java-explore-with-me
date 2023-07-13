package ru.dimax.stats.server.service;


import ru.dimax.stats.dto.EndpointHitDto;
import ru.dimax.stats.dto.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatService {

    EndpointHitDto saveHit(EndpointHitDto endpointHitDto);

    List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, String[] uris, Boolean unique);
}

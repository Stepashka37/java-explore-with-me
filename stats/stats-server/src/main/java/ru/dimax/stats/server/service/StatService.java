package ru.dimax.stats.server.service;


import ru.dimax.stats.dto.EndpointHit;
import ru.dimax.stats.dto.EndpointHitDto;
import ru.dimax.stats.dto.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatService {

    EndpointHit saveHit(EndpointHit endpointHit);

    List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}

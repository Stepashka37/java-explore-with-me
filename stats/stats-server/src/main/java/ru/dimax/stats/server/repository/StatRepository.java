package ru.dimax.stats.server.repository;

import ru.dimax.stats.dto.ViewStats;
import ru.dimax.stats.dto.EndpointHit;

import java.time.LocalDateTime;
import java.util.List;

public interface StatRepository  {


    List<ViewStats> getViewStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);

    List<ViewStats> getAllViewStats(LocalDateTime start, LocalDateTime end, Boolean unique);


    EndpointHit saveHit(EndpointHit hit);


}

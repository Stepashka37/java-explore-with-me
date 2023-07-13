package ru.dimax.stats.server.mapper;

import ru.dimax.stats.dto.EndpointHitDto;
import ru.dimax.stats.dto.ViewStats;
import ru.dimax.stats.server.model.EndpointHit;

public class EndpointHitMapper {
    public static EndpointHit dtoToModel(EndpointHitDto dto) {
        return EndpointHit.builder()
                .app(dto.getApp())
                .uri(dto.getUri())
                .ip(dto.getIp())
                .timestamp(dto.getTimestamp())
                .build();
    }

    public static EndpointHitDto modelToDto(EndpointHit model) {
        return EndpointHitDto.builder()
              .app(model.getApp())
              .uri(model.getUri())
              .ip(model.getIp())
              .timestamp(model.getTimestamp())
              .build();
    }

    public static ViewStats hitToViewStats(EndpointHit endpointHit) {
        return ViewStats.builder()
              .app(endpointHit.getApp())
              .uri(endpointHit.getUri())
              .build();
    }
}
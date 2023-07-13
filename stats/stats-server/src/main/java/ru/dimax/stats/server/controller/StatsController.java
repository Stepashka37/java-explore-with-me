package ru.dimax.stats.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.dimax.stats.dto.EndpointHitDto;
import ru.dimax.stats.dto.ViewStats;
import ru.dimax.stats.server.service.StatService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
public class StatsController {

    private final StatService statsService;


    public StatsController(StatService statsService) {
        this.statsService = statsService;
    }

    @PostMapping("/hit")
    public EndpointHitDto saveHit(EndpointHitDto dto) {
        return statsService.saveHit(dto);
    }

    @GetMapping("/stats")
    public List<ViewStats> getStats(@RequestParam(required = true) String start,
                                    @RequestParam(required = true) String end,
                                    @RequestParam(required = false) String[] uris,
                                    @RequestParam(required = false) String unique) {
        LocalDateTime startDT = LocalDateTime.parse(start);
        LocalDateTime endDT = LocalDateTime.parse(end);
        Boolean uniqueBool = Boolean.parseBoolean(unique);
        return statsService.getStats(startDT, endDT, uris, uniqueBool);
    }

}

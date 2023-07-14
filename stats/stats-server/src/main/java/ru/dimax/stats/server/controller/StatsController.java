package ru.dimax.stats.server.controller;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dimax.stats.dto.EndpointHit;
import ru.dimax.stats.dto.ViewStats;
import ru.dimax.stats.server.service.StatService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/")
public class StatsController {

    private final StatService statsService;


    public StatsController(StatService statsService) {
        this.statsService = statsService;
    }



    @GetMapping(value = "/stats")
    public ResponseEntity<List<ViewStats>> getStats(@RequestParam @NonNull String start,
                                                   @RequestParam @NonNull String end,
                                                   @RequestParam(required = false) List<String> uris,
                                                   @RequestParam(required = false, defaultValue = "false") boolean unique) {
        LocalDateTime startDT;
        LocalDateTime endDT;
        try {
           startDT = LocalDateTime.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
           endDT = LocalDateTime.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        if (uris == null) uris = new ArrayList<>();
        List<ViewStats> result = statsService.getStats(startDT, endDT, uris, unique);
        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "/hit")
    @ResponseStatus(code = HttpStatus.CREATED)
    public EndpointHit saveHit(@RequestBody EndpointHit dto) {
        return statsService.saveHit(dto);
    }

}

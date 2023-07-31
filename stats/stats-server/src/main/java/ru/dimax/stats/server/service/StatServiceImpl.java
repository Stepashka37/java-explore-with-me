package ru.dimax.stats.server.service;

import org.springframework.stereotype.Service;
import ru.dimax.stats.dto.EndpointHit;
import ru.dimax.stats.dto.ViewStats;
import ru.dimax.stats.server.repository.dao.EndpointRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class StatServiceImpl implements StatService {
    private final EndpointRepository endpointRepository;

    public StatServiceImpl(EndpointRepository endpointRepository) {
        this.endpointRepository = endpointRepository;
    }

    @Override
    public EndpointHit saveHit(EndpointHit endpointHit) {
       EndpointHit hit = endpointRepository.saveHit(endpointHit);
       return hit;
    }

    @Override
    public List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {

        for (String s : uris) {
            if (s.startsWith("[") && s.endsWith("]")) {
                uris.remove(s);
                uris.add(s.substring(1, s.length() - 1));
            }
        }

        List<ViewStats> viewStats = new ArrayList<>();
        if (uris == null || uris.isEmpty()) {
        viewStats = endpointRepository.getAllViewStats(start, end, unique);
    } else {
            viewStats = endpointRepository.getViewStats(start, end, uris, unique);
        }
    return viewStats;
    }


}

package ru.dimax.stats.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ViewStats {
    private String app;
    private String uri;
    private Long hits;

    public ViewStats() {
    }

    public ViewStats(String app, String uri, Long hits) {
        this.app = app;
        this.uri = uri;
        this.hits = hits;
    }
}

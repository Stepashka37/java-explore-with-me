package ru.dimax.stats.server.repository.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.dimax.stats.dto.ViewStats;
import ru.dimax.stats.dto.EndpointHit;
import ru.dimax.stats.server.repository.StatRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EndpointRepository implements StatRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EndpointRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ViewStats> getViewStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        String sql = new String();
        List<ViewStats> stats = new ArrayList<>();
        if (unique) {
            sql = "SELECT app, uri, COUNT(DISTINCT ip) as count " +
                    "FROM endpoint_hits " +
                    "WHERE timestamp BETWEEN ? AND ? " +
                    "AND uri = ANY(?) " +
                    "GROUP BY app, uri " +
                    "ORDER BY count DESC ";
            Object[] params = new Object[]{start, end, uris.toArray(new String[0])};

           stats = jdbcTemplate.query(sql, params, (rs, rowNum) -> makeViewStat(rs));


        } else {
            sql = "SELECT app, uri, (SELECT COUNT(*) as row_count " +
                    "FROM endpoint_hits as eh " +
                    "WHERE timestamp BETWEEN ? AND ? " +
                    "AND eh.uri = endpoint_hits.uri " +
                    "GROUP BY uri) as count FROM endpoint_hits " +
                    "WHERE timestamp BETWEEN ? AND ? " +
                    "AND uri = ANY(?) " +
                    "GROUP BY app, uri " +
                    "ORDER BY count DESC";
            Object[] params = new Object[]{start, end, start, end, uris.toArray(new String[0])};

            stats = jdbcTemplate.query(sql, params, (rs, rowNum) -> makeViewStat(rs));
        }
    return stats;
    }

    @Override
    public List<ViewStats> getAllViewStats(LocalDateTime start, LocalDateTime end, Boolean unique) {
        String sql = new String();
        List<ViewStats> stats = new ArrayList<ViewStats>();
        if (unique) {
            sql = "SELECT app, uri, COUNT(DISTINCT ip) as count " +
                    "FROM endpoint_hits " +
                    "WHERE timestamp BETWEEN ? AND ? " +
                    "GROUP BY app, uri " +
                    "ORDER BY count DESC";
            stats =  jdbcTemplate.query(sql, (rs, rowNum) -> makeViewStat(rs),
                    start, end);
        } else {
            sql = "SELECT app, uri, (SELECT COUNT(*) as row_count " +
                    "FROM endpoint_hits as eh " +
                    "WHERE timestamp BETWEEN ? AND ? " +
                    "AND eh.uri = endpoint_hits.uri " +
                    "GROUP BY uri) as count FROM endpoint_hits " +
                    "WHERE timestamp BETWEEN ? AND ? " +
                    "GROUP BY app, uri " +
                    "ORDER BY count DESC";
            stats =  jdbcTemplate.query(sql, (rs, rowNum) -> makeViewStat(rs),
                    start, end, start, end);
        };
        return stats;
    }

    @Override
    public EndpointHit saveHit(EndpointHit hit) {
        String sql = "INSERT INTO endpoint_hits (app, uri, ip, timestamp) VALUES (?,?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"id"});
            stmt.setString(1, hit.getApp());
            stmt.setString(2, hit.getUri());
            stmt.setString(3, hit.getIp());
            stmt.setTimestamp(4, Timestamp.valueOf(hit.getTimestamp()));
            return stmt;
        }, keyHolder);

        Long key = keyHolder.getKey().longValue();
        hit.setId(key);
        return hit;
    }

    private ViewStats makeViewStat(ResultSet rs) throws SQLException {
        return ViewStats.builder()
              .app(rs.getString("app"))
              .uri(rs.getString("uri"))
                .hits(rs.getLong("count"))
              .build();
    }

    private EndpointHit makeEndpointHit(ResultSet rs) throws SQLException {
        return EndpointHit.builder()
                .id(rs.getLong("id"))
                .uri(rs.getString("uri"))
                .app(rs.getString("app"))
                .ip(rs.getString("ip"))
                .timestamp(rs.getTimestamp("timestamp").toLocalDateTime())
                .build();
    }


}

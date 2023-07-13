package ru.dimax.stats.server.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.dimax.stats.dto.ViewStats;
import ru.dimax.stats.server.model.EndpointHit;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface StatRepository extends JpaRepository<EndpointHit, Long> {

   @Query("select eh from endpoint_hit as eh where " +
            "eh.timestamp between ?1 and ?2 and " +
            "eh.uri in ?3")
    List<EndpointHit> findByTimestampBetweenAndUriIn(LocalDateTime start, LocalDateTime end, String[] uris);

    /*@Query("select eh.app, eh.uri, (select count(en.uri) from endpoint_hit as en where " +
            "eh.timestamp between :start and :end and " +
            "eh.uri in :uris) from endpoint_hit as eh where " +
            "eh.timestamp between :start and :end and " +
            "eh.uri in :uris")
    List<EndpointHit> findByTimestampBetweenAndUriIn(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end, @Param("uris") String[] uris);*/


}

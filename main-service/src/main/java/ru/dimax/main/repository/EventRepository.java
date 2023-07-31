package ru.dimax.main.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.dimax.main.model.Event;
import ru.dimax.main.model.State;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    Event findByIdAndInitiatorId(Long id, Long initiatorId);

    List<Event> findAllByInitiatorId(Long initiatorId, Pageable pageable);

    @Query("SELECT e FROM Event e " +
            "WHERE (:userIds IS NULL OR e.initiator.id IN :userIds) " +
            "AND (:states IS NULL OR e.state IN :states) " +
            "AND (:categoriesIds IS NULL OR e.category.id IN :categoriesIds) " +
            "AND e.eventDate BETWEEN :rangeStart AND :rangeEnd")
    List<Event> searchEvents(@Param("userIds") List<Long> userIds,
                             @Param("states") List<State> states,
                             @Param("categoriesIds") List<Long> categoriesIds,
                             @Param("rangeStart") LocalDateTime rangeStart,
                             @Param("rangeEnd") LocalDateTime rangeEnd,
                             Pageable pageable);

    @Query("SELECT e FROM Event e " +
            "WHERE e.state = 'PUBLISHED' " +
            "AND (:text IS NULL OR (LOWER(e.annotation) LIKE LOWER(:text) OR LOWER(e.description) LIKE LOWER(:text))) " +
            "AND (:categories IS NULL OR e.category.id IN :categories) " +
            "AND (:paid IS NULL OR e.paid = :paid) " +
            "AND e.eventDate BETWEEN :rangeStart AND :rangeEnd " +
            "AND (:available = true AND size(e.requests) < e.participantLimit " +
            "     OR :available = false) " +
            "ORDER BY e.views DESC")
    List<Event> findEventsByFiltersSortByViews(@Param("text") String text,
                                   @Param("categories") List<Long> categories,
                                   @Param("paid") Boolean paid,
                                   @Param("rangeStart") LocalDateTime rangeStart,
                                   @Param("rangeEnd") LocalDateTime rangeEnd,
                                   @Param("available") Boolean available,
                                   Pageable pageable);

    @Query("SELECT e FROM Event e " +
            "WHERE e.state = 'PUBLISHED' " +
            "AND (:text IS NULL OR (LOWER(e.annotation) LIKE LOWER(:text) OR LOWER(e.description) LIKE LOWER(:text))) " +
            "AND (:categories IS NULL OR e.category.id IN :categories) " +
            "AND (:paid IS NULL OR e.paid = :paid) " +
            "AND e.eventDate BETWEEN :rangeStart AND :rangeEnd " +
            "AND (:available = true AND size(e.requests) < e.participantLimit " +
            "     OR :available = false) " +
            "ORDER BY e.eventDate DESC")
    List<Event> findEventsByFiltersSortByEventDate(@Param("text") String text,
                                               @Param("categories") List<Long> categories,
                                               @Param("paid") Boolean paid,
                                               @Param("rangeStart") LocalDateTime rangeStart,
                                               @Param("rangeEnd") LocalDateTime rangeEnd,
                                               @Param("available") Boolean available,
                                               Pageable pageable);

}

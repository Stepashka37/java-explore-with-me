package ru.dimax.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.dimax.main.model.Request;
import ru.dimax.main.model.State;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findAllByRequesterId(Long requesterId);

    List<Request> findAllByEventId(Long eventId);

    Request findByRequesterIdAndEventId(Long requesterId, Long eventId);

    @Modifying
    @Query("UPDATE Request r SET r.status = :newStatus WHERE r.id = :requestId")
    void updateRequestStatus(@Param("requestId") Long requestId, @Param("newStatus") State newStatus);
}

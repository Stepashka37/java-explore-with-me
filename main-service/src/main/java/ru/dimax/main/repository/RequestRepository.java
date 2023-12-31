package ru.dimax.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dimax.main.model.Request;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findAllByRequesterId(Long requesterId);

    List<Request> findAllByEventId(Long eventId);

    Request findByRequesterIdAndEventId(Long requesterId, Long eventId);

}

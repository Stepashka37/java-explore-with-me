package ru.dimax.main.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dimax.main.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

     List<User> findAllByIdIn(List<Long> ids, Pageable page);

     Optional<User> findByEmail(String email);

}

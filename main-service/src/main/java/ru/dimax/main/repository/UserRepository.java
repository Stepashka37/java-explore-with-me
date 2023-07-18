package ru.dimax.main.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.dimax.main.model.User;

import java.lang.annotation.Native;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

     List<User> findAllByIdIn(List<Long> ids, Pageable page);

}

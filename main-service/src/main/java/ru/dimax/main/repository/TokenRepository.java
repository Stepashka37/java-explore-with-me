package ru.dimax.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.dimax.main.model.Token;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query(
            "Select t from Token t inner join User u on t.user.id = u.id " +
                    "where u.id = :userId and (t.expired = false or t.revoked = false)"
    )
    List<Token> findAllValidTokensByUser(Long userId);

    Optional<Token> findByToken(String token);
}
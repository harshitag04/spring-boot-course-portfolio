package org.example.jwtauthentication.repositories;

import org.example.jwtauthentication.entities.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<SessionEntity, Long> {

    Optional<SessionEntity> findByUserId(Long userId);

    Optional<SessionEntity> findByToken(String token);

    boolean existsByToken(String token);

    void deleteByUserId(Long userId);

}

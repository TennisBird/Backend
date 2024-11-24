package org.tennis_bird.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.tennis_bird.core.entities.PersonEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, UUID> {

    @Query("SELECT p FROM PersonEntity p WHERE p.mailAddress = :mail")
    Optional<PersonEntity> findByMailAddress(@Param("mail") String mail);

    @Query("SELECT p FROM PersonEntity p WHERE p.login = :login")
    Optional<PersonEntity> findByLogin(@Param("login") String login);
}

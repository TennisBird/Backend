package org.tennis_bird.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tennis_bird.core.entities.PersonEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, UUID> {
    Optional<PersonEntity> findByMailAddress(String mail);
}

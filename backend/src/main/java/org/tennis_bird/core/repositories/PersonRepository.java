package org.tennis_bird.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tennis_bird.domain.entities.PersonEntity;

public interface PersonRepository extends JpaRepository<PersonEntity, Long> {
}

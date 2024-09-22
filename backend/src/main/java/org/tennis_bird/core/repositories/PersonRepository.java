package org.tennis_bird.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tennis_bird.core.entities.PersonEntity;

public interface PersonRepository extends JpaRepository<PersonEntity, Long> {
}

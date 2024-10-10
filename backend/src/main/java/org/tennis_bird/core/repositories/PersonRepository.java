package org.tennis_bird.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tennis_bird.core.entities.PersonEntity;

import java.util.UUID;

public interface PersonRepository extends JpaRepository<PersonEntity, UUID> { }

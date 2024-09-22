package org.tennis_bird.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tennis_bird.domain.entities.PersonEntity;
import org.tennis_bird.domain.entities.WorkerEntity;

public interface WorkerRepository extends JpaRepository<WorkerEntity, Long> {
}

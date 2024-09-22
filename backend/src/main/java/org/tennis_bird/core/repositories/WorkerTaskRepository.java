package org.tennis_bird.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tennis_bird.domain.entities.PersonEntity;
import org.tennis_bird.domain.entities.WorkerTaskEntity;

public interface WorkerTaskRepository extends JpaRepository<WorkerTaskEntity, Long> {
}

package org.tennis_bird.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tennis_bird.core.entities.WorkerTaskEntity;

public interface WorkerTaskRepository extends JpaRepository<WorkerTaskEntity, Long> {
}

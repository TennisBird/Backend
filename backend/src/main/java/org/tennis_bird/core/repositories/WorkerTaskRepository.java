package org.tennis_bird.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.tennis_bird.core.entities.TaskEntity;
import org.tennis_bird.core.entities.WorkerEntity;
import org.tennis_bird.core.entities.WorkerTaskEntity;

import javax.transaction.Transactional;
import java.util.List;

public interface WorkerTaskRepository extends JpaRepository<WorkerTaskEntity, Long> {
    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("SELECT wt.worker FROM WorkerTaskEntity wt " +
            "WHERE wt.task = :task AND wt.workerRole = 'author' ")
    List<WorkerEntity> findAuthorsOfTask(@Param(value = "task") TaskEntity task);
}

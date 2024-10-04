package org.tennis_bird.core.repositories.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tennis_bird.core.entities.TaskEntity;
import org.tennis_bird.core.entities.WorkerEntity;
import org.tennis_bird.core.entities.WorkerTaskEntity;
import org.tennis_bird.core.repositories.TaskRepository;
import org.tennis_bird.core.repositories.TestRepositorySupport;
import org.tennis_bird.core.repositories.WorkerTaskRepository;
import org.tennis_bird.core.services.WorkerTaskService;

public class WorkerTaskRepositoryTest extends TestRepositorySupport {
    @Autowired
    WorkerTaskRepository workerTaskRepository;

    @Test
    public void testCreateAndFindTeam() {
        WorkerTaskEntity workerTask = saveWorkerTaskEntity();
        Assertions.assertTrue(workerTaskRepository.findById(workerTask.getId()).isPresent());
    }

    @Test
    public void testDeleteTeam() {
        WorkerTaskEntity workerTask = saveWorkerTaskEntity();
        Assertions.assertTrue(workerTaskRepository.findById(workerTask.getId()).isPresent());

        workerTaskRepository.delete(workerTask);
        Assertions.assertFalse(workerTaskRepository.findById(workerTask.getId()).isPresent());
    }
}

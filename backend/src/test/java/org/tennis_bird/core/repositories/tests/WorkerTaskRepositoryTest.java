package org.tennis_bird.core.repositories.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tennis_bird.core.entities.WorkerTaskEntity;
import org.tennis_bird.core.repositories.TestRepositorySupport;
import org.tennis_bird.core.repositories.WorkerTaskRepository;

public class WorkerTaskRepositoryTest extends TestRepositorySupport {
    @Autowired
    WorkerTaskRepository workerTaskRepository;

    @Test
    public void testCreateAndFindWorkerTask() {
        WorkerTaskEntity workerTask = saveWorkerTaskEntity("role");
        Assertions.assertTrue(workerTaskRepository.findById(workerTask.getId()).isPresent());
    }

    @Test
    public void testDeleteWorkerTask() {
        WorkerTaskEntity workerTask = saveWorkerTaskEntity("role");
        Assertions.assertTrue(workerTaskRepository.findById(workerTask.getId()).isPresent());

        workerTaskRepository.delete(workerTask);
        Assertions.assertFalse(workerTaskRepository.findById(workerTask.getId()).isPresent());
    }

    @Test
    public void testFindWorkersWithRole() {
        WorkerTaskEntity workerTask = saveWorkerTaskEntity("role");
        Assertions.assertEquals(1,
                workerTaskRepository.findWorkersWithRoleOfTask(workerTask.getTask(), "role").size());
        workerTaskRepository.delete(workerTask);
        Assertions.assertTrue(workerTaskRepository.findWorkersWithRoleOfTask(workerTask.getTask(), "role").isEmpty());
    }
}

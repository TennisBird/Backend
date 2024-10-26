package org.tennis_bird.core.repositories.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tennis_bird.core.entities.WorkerEntity;
import org.tennis_bird.core.repositories.TestRepositorySupport;
import org.tennis_bird.core.repositories.WorkerRepository;

class WorkerRepositoryTest extends TestRepositorySupport {
    @Autowired
    WorkerRepository workerRepository;

    @Test
    void testCreateAndFindWorker() {
        WorkerEntity worker = saveWorkerEntity();
        Assertions.assertTrue(workerRepository.findById(worker.getId()).isPresent());
    }

    @Test
    void testDeleteWorker() {
        WorkerEntity worker = saveWorkerEntity();
        Assertions.assertTrue(workerRepository.findById(worker.getId()).isPresent());

        workerRepository.delete(worker);
        Assertions.assertFalse(workerRepository.findById(worker.getId()).isPresent());
    }

    @Test
    void testChangeRole() {
        WorkerEntity worker = saveWorkerEntity();

        Assertions.assertEquals(1, workerRepository.changeRole("new_role", worker.getId()));
        Assertions.assertTrue(workerRepository.findById(worker.getId()).isPresent());
        Assertions.assertEquals("new_role", workerRepository.findById(worker.getId()).get().getPersonRole());
    }
}

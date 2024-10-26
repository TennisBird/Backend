package org.tennis_bird.core.repositories.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tennis_bird.core.entities.TaskEntity;
import org.tennis_bird.core.repositories.TaskRepository;
import org.tennis_bird.core.repositories.TestRepositorySupport;

class TaskRepositoryTest extends TestRepositorySupport {
    @Autowired
    TaskRepository taskRepository;

    @Test
    void testCreateAndFindTask() {
        TaskEntity task = saveTaskEntity();
        Assertions.assertTrue(taskRepository.findByCode(task.getCode()).isPresent());
    }

    @Test
    void testDeleteTask() {
        TaskEntity task = saveTaskEntity();
        Assertions.assertTrue(taskRepository.findById(task.getId()).isPresent());

        taskRepository.delete(task);
        Assertions.assertFalse(taskRepository.findById(task.getId()).isPresent());
    }
}

package org.tennis_bird.core.repositories.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tennis_bird.core.entities.TaskEntity;
import org.tennis_bird.core.entities.TeamEntity;
import org.tennis_bird.core.entities.WorkerEntity;
import org.tennis_bird.core.repositories.TaskRepository;
import org.tennis_bird.core.repositories.TeamRepository;
import org.tennis_bird.core.repositories.TestRepositorySupport;
import org.tennis_bird.core.repositories.WorkerTaskRepository;

public class TaskRepositoryTest extends TestRepositorySupport {
    @Autowired
    TaskRepository taskRepository;

    @Test
    public void testCreateAndFindTask() {
        TaskEntity task = saveTaskEntity();
        Assertions.assertTrue(taskRepository.findByCode(task.getCode()).isPresent());
    }

    @Test
    public void testDeleteTask() {
        TaskEntity task = saveTaskEntity();
        Assertions.assertTrue(taskRepository.findById(task.getId()).isPresent());

        taskRepository.delete(task);
        Assertions.assertFalse(taskRepository.findById(task.getId()).isPresent());
    }

    @Test
    public void testChangeTitle() {
        TaskEntity task = saveTaskEntity();

        Assertions.assertEquals(taskRepository.changeTitle(task.getCode(), "new_title"), 1);
        Assertions.assertEquals(taskRepository.findById(task.getId()).get().getTitle(), "new_title");
    }

    @Test
    public void testChangeDescription() {
        TaskEntity task = saveTaskEntity();

        String newDescription = "some interesting";
        Assertions.assertEquals(taskRepository.addDescription(task.getCode(), newDescription), 1);
        Assertions.assertEquals(taskRepository.findById(task.getId()).get().getDescription(), newDescription);
    }

    @Test
    public void testChangeStatus() {
        TaskEntity task = saveTaskEntity();

        String newStatus = "in progress";
        Assertions.assertEquals(taskRepository.changeStatus(task.getCode(), newStatus), 1);
        Assertions.assertEquals(taskRepository.findById(task.getId()).get().getStatus(), newStatus);
    }

    @Test
    public void testChangePriority() {
        TaskEntity task = saveTaskEntity();

        String newPriority = "high";
        Assertions.assertEquals(taskRepository.changePriority(task.getCode(), newPriority), 1);
        Assertions.assertEquals(taskRepository.findById(task.getId()).get().getPriority(), newPriority);
    }

    @Test
    public void testChangeEstimate() {
        TaskEntity task = saveTaskEntity();

        Assertions.assertEquals(taskRepository.setEstimate(task.getCode(), 5), 1);
        Assertions.assertEquals(taskRepository.findById(task.getId()).get().getEstimate(), 5);
    }
}

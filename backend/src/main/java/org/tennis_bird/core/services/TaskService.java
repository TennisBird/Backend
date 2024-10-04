package org.tennis_bird.core.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.tennis_bird.core.entities.PersonEntity;
import org.tennis_bird.core.entities.TaskEntity;
import org.tennis_bird.core.entities.TeamEntity;
import org.tennis_bird.core.entities.WorkerEntity;
import org.tennis_bird.core.repositories.TaskRepository;
import org.tennis_bird.core.repositories.TeamRepository;

import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class TaskService {
    @Autowired
    TaskRepository repository;
    private static final Logger logger = LogManager.getLogger(TaskService.class.getName());
    public TaskEntity create(TaskEntity task) {
        logger.info("create task with id " + task.getId());
        return repository.save(task);
    }

    public Optional<TaskEntity> findByCode(String code) {
        logger.info("find task by code " + code);
        return repository.findByCode(code);
    }

    public List<TaskEntity> findAll() {
        return repository.findAll();
    }

    public boolean delete(Long id) {
        logger.info("delete task with id " + id);
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public void updateTitle(TaskEntity task, String title) {
        logger.info("update task with code " + task.getCode() + " set title " + title);
        repository.changeTitle(task.getCode(), title);
    }

    public void updateDescription(TaskEntity task, String description) {
        logger.info("update task with code " + task.getCode() + " set description " + description);
        repository.addDescription(task.getCode(), description);
    }

    public void updateStatus(TaskEntity task, String status) {
        logger.info("update task with code " + task.getCode() + " set status " + status);
        repository.changeStatus(task.getCode(), status);
    }

    public void updatePriority(TaskEntity task, String priority) {
        logger.info("update task with code " + task.getCode() + " set priority " + priority);
        repository.changePriority(task.getCode(), priority);
    }

    public void updateEstimate(TaskEntity task, int estimate) {
        logger.info("update task with code " + task.getCode() + " set estimate " + estimate);
        repository.setEstimate(task.getCode(), estimate);
    }
}

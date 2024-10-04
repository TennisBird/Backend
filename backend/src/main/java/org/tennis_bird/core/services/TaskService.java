package org.tennis_bird.core.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
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
        logger.info("create team with id " + task.getId());
        return repository.save(task);
    }

    public Optional<TaskEntity> findByCode(String code) {
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

    public void setAuthor(String taskCode, WorkerEntity worker) {
        logger.info("set worker with id " + worker.getId() + " as author to task with task code " + taskCode);
        repository.setAuthor(taskCode, worker);
    }
}

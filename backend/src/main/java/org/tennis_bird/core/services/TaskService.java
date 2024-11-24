package org.tennis_bird.core.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.tennis_bird.core.entities.TaskEntity;
import org.tennis_bird.core.repositories.TaskRepository;

import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class TaskService {
    @Autowired
    TaskRepository repository;
    private static final Logger logger = LogManager.getLogger(TaskService.class.getName());
    public TaskEntity create(TaskEntity task) {
        logger.info("create task with id {}", task.getId());
        return repository.save(task);
    }

    public Optional<TaskEntity> findByCode(String code) {
        logger.info("find task by code {}", code);
        return repository.findByCode(code);
    }

    public Optional<TaskEntity> update(TaskEntity task) {
        if (repository.findById(task.getId()).isPresent()) {
            logger.info("update task {}", task);
            return Optional.of(repository.save(task));
        }
        logger.info("try update task but it not exist {}", task);
        return Optional.empty();
    }

    public List<TaskEntity> findAll() {
        return repository.findAll();
    }

    public boolean delete(Long id) {
        logger.info("delete task with id {}", id);
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}

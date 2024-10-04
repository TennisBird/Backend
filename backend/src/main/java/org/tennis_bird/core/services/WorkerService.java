package org.tennis_bird.core.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tennis_bird.core.entities.TeamEntity;
import org.tennis_bird.core.entities.WorkerEntity;
import org.tennis_bird.core.repositories.TeamRepository;
import org.tennis_bird.core.repositories.WorkerRepository;

import java.util.List;
import java.util.Optional;

@Component
public class WorkerService {
    @Autowired
    WorkerRepository repository;
    private static final Logger logger = LogManager.getLogger(WorkerService.class.getName());
    public WorkerEntity create(WorkerEntity worker) {
        logger.info("create team with id " + worker.getId());
        return repository.save(worker);
    }

    public Optional<WorkerEntity> find(Long workerId) {
        return repository.findById(workerId);
    }

    public List<WorkerEntity> findAll() {
        return repository.findAll();
    }

    public boolean delete(Long id) {
        logger.info("delete worker with id " + id);
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}

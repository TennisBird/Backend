package org.tennis_bird.core.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tennis_bird.core.entities.TaskEntity;
import org.tennis_bird.core.entities.WorkerEntity;
import org.tennis_bird.core.entities.WorkerTaskEntity;
import org.tennis_bird.core.repositories.TaskRepository;
import org.tennis_bird.core.repositories.WorkerRepository;
import org.tennis_bird.core.repositories.WorkerTaskRepository;

import java.util.List;
import java.util.Optional;

@Component
public class WorkerTaskService {
    @Autowired
    WorkerTaskRepository workerTaskRepository;
    @Autowired
    WorkerRepository workerRepository;
    @Autowired
    TaskRepository taskRepository;
    private static final Logger logger = LogManager.getLogger(WorkerTaskService.class.getName());
    public WorkerTaskEntity create(WorkerTaskEntity workerTask) {
        logger.info("create team with id " + workerTask.getId());
        return workerTaskRepository.save(workerTask);
    }

    public Optional<WorkerTaskEntity> find(Long workerTaskId) {
        return workerTaskRepository.findById(workerTaskId);
    }

    public List<WorkerTaskEntity> findAll() {
        return workerTaskRepository.findAll();
    }

    public boolean delete(Long id) {
        logger.info("delete worker task with id " + id);
        if (workerTaskRepository.existsById(id)) {
            workerTaskRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public WorkerTaskEntity setAuthor(String taskCode, WorkerEntity worker) {
        logger.info("set worker with id " + worker.getId() + " as author to task with task code " + taskCode);
        WorkerTaskEntity workerTask = new WorkerTaskEntity();
        workerTask.setTask(taskRepository.findByCode(taskCode).get());
        workerTask.setWorker(worker);
        workerTask.setWorkerRole("author");
        return workerTaskRepository.save(workerTask);
    }

    public List<WorkerEntity> getAuthorsForTask(String code) {
        TaskEntity task = taskRepository.findByCode(code).get();
        return workerTaskRepository.findAuthorsOfTask(task);
    }
    //TODO
    /*
    set executors observers
     */
}

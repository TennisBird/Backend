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
public class WorkerOnTaskService {
    @Autowired
    WorkerTaskRepository workerTaskRepository;
    @Autowired
    WorkerRepository workerRepository;
    @Autowired
    TaskRepository taskRepository;
    private static final Logger logger = LogManager.getLogger(WorkerOnTaskService.class.getName());
    public WorkerTaskEntity create(WorkerTaskEntity workerTask) {
        logger.info("set worker " + workerTask.getWorker().getId() +
                " on task " + workerTask.getTask().getId() +
                " with role " + workerTask.getWorkerRole());
        return workerTaskRepository.save(workerTask);
    }

    public Optional<WorkerTaskEntity> find(Long workerTaskId) {
        logger.info("find link between worker and task by id " + workerTaskId);
        return workerTaskRepository.findById(workerTaskId);
    }

    public List<WorkerTaskEntity> findAll() {
        return workerTaskRepository.findAll();
    }

    public boolean delete(Long id) {
        logger.info("delete link between worker and task with id " + id);
        if (workerTaskRepository.existsById(id)) {
            workerTaskRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public WorkerTaskEntity setWorkerOnTask(String taskCode, WorkerEntity worker, String workerRole) {
        logger.info("set worker with id " + worker.getId() + " as " + workerRole + " to task with task code " + taskCode);
        WorkerTaskEntity workerTask = new WorkerTaskEntity();
        workerTask.setTask(taskRepository.findByCode(taskCode).get());
        workerTask.setWorker(worker);
        workerTask.setWorkerRole(workerRole);
        return workerTaskRepository.save(workerTask);
    }

    public List<WorkerEntity> getWorkersWithRoleForTask(String code, String role) {
        logger.info("get workers of task " + code + " with role " + role);
        TaskEntity task = taskRepository.findByCode(code).get();
        return workerTaskRepository.findWorkersWithRoleOfTask(task, role);
    }
}

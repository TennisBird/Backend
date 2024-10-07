package org.tennis_bird.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tennis_bird.core.entities.*;
import org.tennis_bird.core.services.TaskService;
import org.tennis_bird.core.services.TeamService;
import org.tennis_bird.core.services.WorkerService;
import org.tennis_bird.core.services.WorkerTaskService;

import java.util.List;
import java.util.UUID;

@RestController
public class TaskController {
    @Autowired
    TaskService taskService;
    @Autowired
    WorkerService workerService;
    @Autowired
    WorkerTaskService workerTaskService;
    private static final Logger logger = LogManager.getLogger(TaskController.class.getName());
    @PostMapping(path = "/task/",
            consumes = "application/json",
            produces = "application/json")
    public TaskEntity createTask(@RequestBody TaskEntity request) {
        logger.info(request);
        return taskService.create(request);
    }

    @GetMapping(path = "/task/{code}",
            produces = "application/json")
    public TaskEntity getTask(@PathVariable(value = "code") String code) {
        logger.info(code);
        return taskService.findByCode(code).get();
    }

    @DeleteMapping(path = "/task/{id}", produces = "application/json")
    public ResponseEntity<Void> deleteTask(@PathVariable(value = "id") Long id) {
        logger.info("Attempting to delete task with id: " + id);
        if (!taskService.delete(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/task/{code}/role/{role}",
            produces = "application/json")
    public void setWorkerOnTask(
            @PathVariable(value = "code") String code,
            @PathVariable(value = "role") String role,
            @RequestParam(value = "author_id") Long authorId
    ) {
        logger.info(code, authorId);
        WorkerEntity author = workerService.find(authorId).get();
        workerTaskService.setWorkerOnTask(code, author, role);
    }

    @GetMapping(path = "/task/{code}/role/{role}",
            produces = "application/json")
    public List<WorkerEntity> getWorkersWithRoleOnTask(
            @PathVariable(value = "code") String code,
            @PathVariable(value = "role") String role
    ) {
        logger.info(code, "get authors");
        return workerTaskService.getWorkersWithRoleForTask(code, role);
    }

    //TODO
    /*
    update
    "title" String title;
    "description" String description;
    "status" String status = "open";
    "priority" String priority = "medium";
    "estimate" int estimate;
     */
}

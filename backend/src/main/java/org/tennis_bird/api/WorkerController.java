package org.tennis_bird.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tennis_bird.core.entities.PersonEntity;
import org.tennis_bird.core.entities.TeamEntity;
import org.tennis_bird.core.entities.WorkerEntity;
import org.tennis_bird.core.services.PersonService;
import org.tennis_bird.core.services.TeamService;
import org.tennis_bird.core.services.WorkerService;

import java.util.List;
import java.util.UUID;

@RestController
public class WorkerController {
    @Autowired
    private WorkerService workerService;
    @Autowired
    private PersonService personService;
    @Autowired
    private TeamService teamService;
    private static final Logger logger = LogManager.getLogger(WorkerController.class.getName());

    @PostMapping(path = "/worker/",
            produces = "application/json")
    public WorkerEntity createWorker(
            @RequestParam(value = "person_id") UUID personId,
            @RequestParam(value = "team_id") Long teamId,
            @RequestParam(value = "person_role") String role
    ) {
        logger.info(personId + " " + teamId + " " + role);
        PersonEntity person = personService.find(personId).get();
        TeamEntity team = teamService.find(teamId).get();
        return workerService.create(new WorkerEntity(null, person, team, role));
    }

    @GetMapping(path = "/worker/{id}",
            produces = "application/json")
    public WorkerEntity getWorker(@PathVariable(value = "id") Long id) {
        logger.info(id);
        return workerService.find(id).get();
    }

    @DeleteMapping(path = "/worker/{id}", produces = "application/json")
    public ResponseEntity<Void> deleteWorker(@PathVariable(value = "id") Long id) {
        logger.info("Attempting to delete team with id: " + id);
        if (!workerService.delete(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}

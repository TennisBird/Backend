package org.tennis_bird.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tennis_bird.api.data.InfoConverter;
import org.tennis_bird.api.data.WorkerInfoResponse;
import org.tennis_bird.core.entities.PersonEntity;
import org.tennis_bird.core.entities.TeamEntity;
import org.tennis_bird.core.entities.WorkerEntity;
import org.tennis_bird.core.services.PersonService;
import org.tennis_bird.core.services.TeamService;
import org.tennis_bird.core.services.WorkerService;

import java.util.Optional;
import java.util.UUID;

@RestController
public class WorkerController {
    @Autowired
    private WorkerService workerService;
    @Autowired
    private PersonService personService;
    @Autowired
    private TeamService teamService;
    @Autowired
    InfoConverter converter;
    private static final Logger logger = LogManager.getLogger(WorkerController.class.getName());

    @PostMapping(path = "/worker/",
            produces = "application/json")
    public Optional<WorkerInfoResponse> createWorker(
            @RequestParam(value = "person_id") UUID personId,
            @RequestParam(value = "team_id") Long teamId,
            @RequestParam(value = "person_role") String role
    ) {
        logger.info(personId + " " + teamId + " " + role);
        Optional<PersonEntity> person = personService.find(personId);
        Optional<TeamEntity> team = teamService.find(teamId);
        return person.isPresent() && team.isPresent()
                ? Optional.of(converter.entityToResponse(workerService.create(new WorkerEntity(null, person.get(), team.get(), role))))
                : Optional.empty();
    }

    @GetMapping(path = "/worker/{id}",
            produces = "application/json")
    public Optional<WorkerInfoResponse> getWorker(@PathVariable(value = "id") Long id) {
        logger.info(id);
        return workerService.find(id).map(w -> converter.entityToResponse(w));
    }

    @PutMapping(path = "/worker/{id}/role",
            produces = "application/json")
    public boolean updateWorkerRole(
            @PathVariable(value = "id") Long id,
            @RequestParam(value = "role") String role
    ) {
        logger.info(id);
        return workerService.changeRole(id, role) != 0;
    }

    @DeleteMapping(path = "/worker/{id}", produces = "application/json")
    public boolean deleteWorker(@PathVariable(value = "id") Long id) {
        logger.info("Attempting to delete team with id: " + id);
       return workerService.delete(id);
    }
}

package org.tennis_bird.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tennis_bird.core.entities.PersonEntity;
import org.tennis_bird.core.entities.TeamEntity;
import org.tennis_bird.core.services.TeamService;

import java.util.List;
import java.util.UUID;

@RestController
public class TeamController {
    @Autowired
    TeamService teamService;
    private static final Logger logger = LogManager.getLogger(TeamController.class.getName());
    @PostMapping(path = "/team/",
            consumes = "application/json",
            produces = "application/json")
    public TeamEntity createTeam(@RequestBody TeamEntity request) {
        logger.info(request);
        return teamService.create(request);
    }

    @GetMapping(path = "/team/{id}",
            produces = "application/json")
    public TeamEntity getTeam(@PathVariable(value = "id") Long id) {
        logger.info(id);
        return teamService.find(id).get();
    }

    @DeleteMapping(path = "/team/{id}", produces = "application/json")
    public ResponseEntity<Void> deleteTeam(@PathVariable(value = "id") Long id) {
        logger.info("Attempting to delete team with id: " + id);
        if (!teamService.delete(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

}

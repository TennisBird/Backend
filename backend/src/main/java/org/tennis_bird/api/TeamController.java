package org.tennis_bird.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tennis_bird.core.entities.TeamEntity;
import org.tennis_bird.core.services.TeamService;

import java.util.Optional;


@RestController
public class TeamController {
    @Autowired
    TeamService teamService;

    private static final Logger logger = LogManager.getLogger(TeamController.class.getName());
    @PostMapping(path = "/team/",
            produces = "application/json")
    public TeamEntity createTeam(@RequestParam(value = "name") String name) {
        logger.info(name);
        return teamService.create(new TeamEntity(null, name));
    }

    @GetMapping(path = "/team/{id}",
            produces = "application/json")
    public Optional<TeamEntity> getTeam(@PathVariable(value = "id") Long id) {
        logger.info(id);
        return teamService.find(id);
    }

    @PutMapping(path = "/team/{id}/name",
            produces = "application/json")
    public boolean updateTeamName(
            @PathVariable(value = "id") Long id,
            @RequestParam(value = "name") String name
    ) {
        logger.info(id);
        return teamService.changeName(id, name) != 0;
    }

    @DeleteMapping(path = "/team/{id}", produces = "application/json")
    public boolean deleteTeam(@PathVariable(value = "id") Long id) {
        logger.info("Attempting to delete team with id: {}", id);
        return teamService.delete(id);
    }
}

package org.tennis_bird.core.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.tennis_bird.core.entities.PersonEntity;
import org.tennis_bird.core.entities.TeamEntity;
import org.tennis_bird.core.repositories.PersonRepository;
import org.tennis_bird.core.repositories.TeamRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@Transactional
public class TeamService {
    @Autowired
    TeamRepository repository;
    private static final Logger logger = LogManager.getLogger(TeamService.class.getName());
    public TeamEntity create(TeamEntity team) {
        logger.info("create team with id " + team.getId());
        return repository.save(team);
    }

    public Optional<TeamEntity> find(Long teamId) {
        return repository.findById(teamId);
    }

    public List<TeamEntity> findAll() {
        return repository.findAll();
    }

    public boolean delete(Long id) {
        logger.info("delete team with id " + id);
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}

package org.tennis_bird.core.repositories.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tennis_bird.core.entities.TeamEntity;
import org.tennis_bird.core.repositories.TeamRepository;
import org.tennis_bird.core.repositories.TestRepositorySupport;

class TeamRepositoryTest extends TestRepositorySupport {
    @Autowired
    TeamRepository teamRepository;

    @Test
    void testCreateAndFindTeam() {
        TeamEntity team = saveTeamEntity();
        Assertions.assertTrue(teamRepository.findById(team.getId()).isPresent());
    }

    @Test
    void testDeleteTeam() {
        TeamEntity team = saveTeamEntity();
        Assertions.assertTrue(teamRepository.findById(team.getId()).isPresent());

        teamRepository.delete(team);
        Assertions.assertFalse(teamRepository.findById(team.getId()).isPresent());
    }

    @Test
    void testChangeName() {
        TeamEntity team = saveTeamEntity();

        Assertions.assertEquals(1, teamRepository.changeName("new_name", team.getId()));
        Assertions.assertEquals("new_name", teamRepository.findById(team.getId()).get().getName());
    }
}

package org.tennis_bird.core.repositories.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tennis_bird.core.entities.TeamEntity;
import org.tennis_bird.core.repositories.TeamRepository;
import org.tennis_bird.core.repositories.TestRepositorySupport;

public class TeamRepositoryTest extends TestRepositorySupport {
    @Autowired
    TeamRepository teamRepository;

    @Test
    public void testCreateAndFindTeam() {
        TeamEntity team = saveTeamEntity();
        Assertions.assertTrue(teamRepository.findById(team.getId()).isPresent());
    }

    @Test
    public void testDeleteTeam() {
        TeamEntity team = saveTeamEntity();
        Assertions.assertTrue(teamRepository.findById(team.getId()).isPresent());

        teamRepository.delete(team);
        Assertions.assertFalse(teamRepository.findById(team.getId()).isPresent());
    }

    @Test
    public void testChangeName() {
        TeamEntity team = saveTeamEntity();

        Assertions.assertEquals(teamRepository.changeName("new_name", team.getId()), 1);
        Assertions.assertEquals(teamRepository.findById(team.getId()).get().getName(), "new_name");
    }
}

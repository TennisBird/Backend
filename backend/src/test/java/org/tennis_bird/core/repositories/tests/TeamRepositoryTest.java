package org.tennis_bird.core.repositories.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.tennis_bird.core.entities.PersonEntity;
import org.tennis_bird.core.entities.TeamEntity;
import org.tennis_bird.core.repositories.TeamRepository;
import org.tennis_bird.core.repositories.TestRepositorySupport;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

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

package org.tennis_bird.core.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.tennis_bird.core.entities.PersonEntity;
import org.tennis_bird.core.entities.TeamEntity;
import org.tennis_bird.core.entities.WorkerEntity;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.Instant;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class WorkerRepositoryTest {
    @Autowired
    PersonRepository personRepository;
    @Autowired
    WorkerRepository workerRepository;
    @Autowired
    TeamRepository teamRepository;

    @Test
    public void testWorkerRepository() {
        PersonEntity person = new PersonEntity();
        person.setFirstName("Kate");
        person.setLastName("Revinskaya");
        person.setMailAddress("xxx@gmail.com");
        person.setNickname("kateee");
        person.setBirthDate(Date.from(Instant.now().minusSeconds(60*60*24*365*19)));
        personRepository.save(person);
        TeamEntity team = new TeamEntity();
        team.setName("team");
        teamRepository.save(team);
        WorkerEntity worker = new WorkerEntity();
        worker.setPersonRole("developer");
        worker.setPerson(person);
        worker.setTeam(team);
        worker = workerRepository.save(worker);
        Assertions.assertEquals(workerRepository.changeRole("dackender", worker.getId()), 1);
    }
}

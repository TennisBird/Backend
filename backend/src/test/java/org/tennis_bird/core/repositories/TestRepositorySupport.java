package org.tennis_bird.core.repositories;

import org.h2.util.Task;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.tennis_bird.core.entities.PersonEntity;
import org.tennis_bird.core.entities.TaskEntity;
import org.tennis_bird.core.entities.TeamEntity;
import org.tennis_bird.core.entities.WorkerEntity;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class TestRepositorySupport {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private WorkerRepository workerRepository;
    @Autowired
    private TaskRepository taskRepository;

    protected PersonEntity savePersonEntity() {
        PersonEntity person = new PersonEntity();
        UUID personUuid = UUID.randomUUID();
        person.setUuid(personUuid);
        person.setFirstName("first_name");
        person.setLastName("last_name");
        person.setMailAddress("mail@gmail.com");
        person.setUsername("username");
        person.setLogin("login");
        person.setPassword("1111");
        person.setTelephoneNumber("+3653332222222");
        person.setBirthDate(Date.from(
                LocalDate.now().minusYears(18).atStartOfDay(ZoneId.systemDefault()).toInstant()
        ));
        personRepository.save(person);
        return person;
    }

    protected TeamEntity saveTeamEntity() {
        TeamEntity team = new TeamEntity();
        team.setName("name");
        teamRepository.save(team);
        return team;
    }

    protected WorkerEntity saveWorkerEntity() {
        PersonEntity person = savePersonEntity();
        TeamEntity team = saveTeamEntity();
        WorkerEntity worker = new WorkerEntity();
        worker.setTeam(team);
        worker.setPerson(person);
        worker.setPersonRole("role");
        workerRepository.save(worker);
        return worker;
    }

    protected TaskEntity saveTaskEntity() {
        TaskEntity task = new TaskEntity();
        task.setCode("DB-001");
        task.setTitle("create schema");
        taskRepository.save(task);
        return task;
    }
}

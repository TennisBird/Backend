package org.tennis_bird.core.repositories;

import org.h2.util.Task;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.tennis_bird.core.entities.*;

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
    @Autowired
    private WorkerTaskRepository workerTaskRepository;

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
        return personRepository.save(person);
    }

    protected TeamEntity saveTeamEntity() {
        TeamEntity team = new TeamEntity();
        team.setName("name");
        return teamRepository.save(team);
    }

    protected WorkerEntity saveWorkerEntity() {
        PersonEntity person = savePersonEntity();
        TeamEntity team = saveTeamEntity();
        WorkerEntity worker = new WorkerEntity();
        worker.setTeam(team);
        worker.setPerson(person);
        worker.setPersonRole("role");
        return workerRepository.save(worker);
    }

    protected TaskEntity saveTaskEntity() {
        TaskEntity task = new TaskEntity();
        task.setCode("DB-001");
        task.setTitle("create schema");
        return taskRepository.save(task);
    }

    protected WorkerTaskEntity saveWorkerTaskEntity() {
        WorkerTaskEntity workerTask = new WorkerTaskEntity();
        workerTask.setTask(saveTaskEntity());
        workerTask.setWorker(saveWorkerEntity());
        workerTask.setWorkerRole("role");
        return workerTaskRepository.save(workerTask);
    }
}

package org.tennis_bird.core.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.tennis_bird.core.entities.PersonEntity;
import org.tennis_bird.core.entities.TeamEntity;
import org.tennis_bird.core.entities.WorkerEntity;
import org.tennis_bird.core.services.PersonService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Date;
import java.time.Instant;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class PersonRepositoryTest {
    @Autowired
    PersonRepository personRepository;

    @Test
    public void testCRUDPersonRepository() {
        PersonEntity person = new PersonEntity();
        UUID personUuid = UUID.randomUUID();
        person.setUuid(personUuid);
        person.setFirstName("Kate");
        person.setLastName("Revinskaya");
        person.setMailAddress("xxx@gmail.com");
        person.setUsername("kateee");
        person.setLogin("katee");
        person.setPassword("12XX545");
        person.setTelephoneNumber("+365334565206");
        person.setBirthDate(Date.from(Instant.now().minusSeconds(60*60*24*365*19)));
        personRepository.save(person);
        Assertions.assertTrue(personRepository.findById(personUuid).isPresent());

        person.setUsername("Kate");
        personRepository.updateOrInsert(person);
        Assertions.assertEquals(personRepository.findById(personUuid).get().getUsername(), "Kate");

        personRepository.delete(person);
        Assertions.assertFalse(personRepository.findById(personUuid).isPresent());
    }
}

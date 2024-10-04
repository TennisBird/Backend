package org.tennis_bird.core.repositories.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.tennis_bird.core.entities.PersonEntity;
import org.tennis_bird.core.repositories.PersonRepository;
import org.tennis_bird.core.repositories.TestRepositorySupport;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

public class PersonRepositoryTest extends TestRepositorySupport {
    @Autowired
    PersonRepository personRepository;

    @Test
    public void testCreateAndFindPerson() {
        PersonEntity person = savePersonEntity();
        Assertions.assertTrue(personRepository.findById(person.getUuid()).isPresent());
    }

    @Test
    public void testDeletePerson() {
        PersonEntity person = savePersonEntity();
        Assertions.assertTrue(personRepository.findById(person.getUuid()).isPresent());

        personRepository.delete(person);
        Assertions.assertFalse(personRepository.findById(person.getUuid()).isPresent());
    }

    @Test
    public void testChangeUsername() {
        PersonEntity person = savePersonEntity();

        Assertions.assertEquals(personRepository.changeUsername("new_username", person.getUuid()), 1);
        Assertions.assertEquals(personRepository.findById(person.getUuid()).get().getUsername(), "new_username");
    }

    @Test
    public void testChangeFirstName() {
        PersonEntity person = savePersonEntity();

        Assertions.assertEquals(personRepository.changeFirstName("new_first_name", person.getUuid()), 1);
        Assertions.assertEquals(personRepository.findById(person.getUuid()).get().getFirstName(), "new_first_name");
    }

    @Test
    public void testChangeLastName() {
        PersonEntity person = savePersonEntity();

        Assertions.assertEquals(personRepository.changeLastName("new_last_name", person.getUuid()), 1);
        Assertions.assertEquals(personRepository.findById(person.getUuid()).get().getLastName(), "new_last_name");
    }

    @Test
    public void testChangeBirthDate() {
        PersonEntity person = savePersonEntity();

        Date newBirthDate = Date.from(
                LocalDate.now().minusYears(19).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Assertions.assertEquals(personRepository.changeBirthDate(newBirthDate, person.getUuid()), 1);
        Assertions.assertEquals(newBirthDate.getTime(),
                personRepository.findById(person.getUuid()).get().getBirthDate().getTime());
    }

    @Test
    public void testChangeTelephoneNumber() {
        PersonEntity person = savePersonEntity();

        Assertions.assertEquals(personRepository.changeTelephoneNumber("+3653332232222", person.getUuid()), 1);
        Assertions.assertEquals(personRepository.findById(person.getUuid()).get().getTelephoneNumber(), "+3653332232222");
    }

    @Test
    public void testChangeMailAddress() {
        PersonEntity person = savePersonEntity();

        Assertions.assertEquals(personRepository.changeMailAddress("new_mail@gmail.com", person.getUuid()), 1);
        Assertions.assertEquals(personRepository.findById(person.getUuid()).get().getMailAddress(), "new_mail@gmail.com");
    }
}

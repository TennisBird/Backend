package org.tennis_bird.core.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.tennis_bird.core.entities.PersonEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PersonServiceTest {
    @Autowired
    PersonService personService;

    @Test
    public void testUpdatePerson() throws ParseException {
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
        Date oldDate = Date.from(
                LocalDate.now().minusYears(18).atStartOfDay(ZoneId.systemDefault()).toInstant());
        person.setBirthDate(oldDate);
        personService.create(person);
        Assertions.assertTrue(personService.find(person.getUuid()).isPresent());
        person.setFirstName("a");
        Date newDate = new SimpleDateFormat("yyyy-MM-dd").parse("2004-10-10");
        person.setBirthDate(newDate);
        personService.update(person);
        Assertions.assertEquals(personService.find(person.getUuid()).get().getFirstName(), "a");
        Assertions.assertEquals(personService.find(person.getUuid()).get().getBirthDate().getTime(), newDate.getTime());
    }
}

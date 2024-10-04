package org.tennis_bird.core.services;

import lombok.extern.log4j.Log4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tennis_bird.core.entities.PersonEntity;
import org.tennis_bird.core.repositories.PersonRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@Transactional
public class PersonService {
    @Autowired
    PersonRepository repository;
    private static final Logger logger = LogManager.getLogger(PersonService.class.getName());
    public PersonEntity create(PersonEntity person) {
        logger.info("create person with uuid " + person.getUuid());
        return repository.save(person);
    }

    public Optional<PersonEntity> find(UUID personUuid) {
        logger.info("find person with uuid " + personUuid);
        return repository.findById(personUuid);
    }

    public List<PersonEntity> findAll() {
        return repository.findAll();
    }

    public boolean delete(UUID uuid) {
        logger.info("delete person with uuid " + uuid);
        if (repository.existsById(uuid)) {
            repository.deleteById(uuid);
            return true;
        }
        return false;
    }

    public void updateUsername(PersonEntity person, String newUsername) {
        logger.info("update person with uuid " + person.getUuid() + " set username " + newUsername);
        repository.changeUsername(newUsername, person.getUuid());
    }

    public void updateFirstName(PersonEntity person, String firstName) {
        logger.info("update person with uuid " + person.getUuid() + " set firstName " + firstName);
        repository.changeFirstName(firstName, person.getUuid());
    }

    public void updateLastName(PersonEntity person, String lastName) {
        logger.info("update person with uuid " + person.getUuid() + " set lastName " + lastName);
        repository.changeLastName(lastName, person.getUuid());
    }

    public void updateBirthDate(PersonEntity person, Date birthDate) {
        logger.info("update person with uuid " + person.getUuid() + " set birthDate " + birthDate.toString());
        repository.changeBirthDate(birthDate, person.getUuid());
    }

    public void updateMailAddress(PersonEntity person, String mailAddress) {
        logger.info("update person with uuid " + person.getUuid() + " set mailAddress " + mailAddress);
        repository.changeMailAddress(mailAddress, person.getUuid());
    }

    public void updateTelephoneNumber(PersonEntity person, String telephoneNumber) {
        logger.info("update person with uuid " + person.getUuid() + " set telephoneNumber " + telephoneNumber);
        repository.changeLastName(telephoneNumber, person.getUuid());
    }
}

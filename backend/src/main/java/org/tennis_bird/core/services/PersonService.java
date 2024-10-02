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

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class PersonService {
    @Autowired
    PersonRepository repository;
    private static final Logger logger = LogManager.getLogger(PersonService.class.getName());
    public void create(PersonEntity person) {
        logger.info("create person with uuid " + person.getUuid());
        repository.save(person);
    }

    public Optional<PersonEntity> find(UUID personUuid) {
        return repository.findById(personUuid);
    }

    public PersonEntity updateUsername(PersonEntity person, String newUsername) {
        logger.info("update person with uuid " + person.getUuid() + "set username " + person.getUsername());
        person.setUsername(newUsername);
        return repository.updateOrInsert(person);
    }

    public void delete(PersonEntity person) {
        logger.info("delete person with uuid " + person.getUuid());
        repository.delete(person);
    }
}

package org.tennis_bird.core.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.tennis_bird.core.entities.PersonEntity;
import org.tennis_bird.core.repositories.PersonRepository;

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

    public Optional<PersonEntity> update(PersonEntity person) {
        if (repository.findById(person.getUuid()).isPresent()) {
            logger.info("update person " + person);
            return Optional.of(repository.save(person));
        }
        logger.info("try update person but it not exist" + person);
        return Optional.empty();
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
}

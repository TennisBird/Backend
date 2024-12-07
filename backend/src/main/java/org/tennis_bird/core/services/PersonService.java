package org.tennis_bird.core.services;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.tennis_bird.core.entities.PersonEntity;
import org.tennis_bird.core.repositories.PersonRepository;
import org.tennis_bird.core.services.email.EmailValidationService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@Transactional
public class PersonService implements UserDetailsService {

    //TODO this doesnt work
    //must have / in the end
    @Value("${avatars.path}")
    private String avatarPath;


    @Autowired
    private EmailValidationService emailValidator;
    @Autowired
    private PersonRepository repository;
    private static final Logger logger = LogManager.getLogger(PersonService.class.getName());

    public PersonEntity create(PersonEntity person) {
        logger.info("create person with uuid {}", person.getUuid());
        // TODO make optional
        if (emailValidator.isValid(person.getMailAddress())) {
            return repository.save(person);
        } else {
            return null;
        }
    }

    public void updateAvatar(MultipartFile avatar, UUID uuid) throws IOException {
        String fullPath = avatarPath + uuid +".png";
        repository.updateAvatar(fullPath, uuid);
        Path pathToSave = Paths.get(fullPath);
        OutputStream stream = Files.newOutputStream(pathToSave);
        stream.write(avatar.getBytes());
        stream.close();
    }

    public Optional<byte[]> getAvatar(UUID uuid) throws IOException {
        Optional<String> path = repository.getAvatarPathByUUID(uuid);
        if(path.isEmpty()) {
            return Optional.empty();
        }

        File file = new File(path.get());
        InputStream inputStream = new FileInputStream(file);
        return Optional.of(IOUtils.toByteArray(inputStream));
    }

    public Optional<PersonEntity> update(PersonEntity person) {
        if (repository.findById(person.getUuid()).isPresent()) {
            logger.info("update person {}", person);
            return Optional.of(repository.save(person));
        }
        logger.info("try update person but it not exist {}", person);
        return Optional.empty();
    }

    public Optional<PersonEntity> find(UUID personUuid) {
        logger.info("find person with uuid {}", personUuid);
        return repository.findById(personUuid);
    }

    // todo make boolean?
    public PersonEntity findByEmail(String email) {
        logger.info("Finding person with email " + email);
        return repository.findByMailAddress(email).orElseThrow();
    }

    public PersonEntity findByLogin(String login) {
        logger.info("Finding person with login " + login);
        return repository.findByLogin(login).orElseThrow();
    }

    public List<PersonEntity> findAll() {
        return repository.findAll();
    }

    public Optional<PersonEntity> verifyUser(UUID uuid) {
        logger.info("Verifying user with uuid {}", uuid);
        Optional<PersonEntity> person = find(uuid);
        person.ifPresent(personEntity -> personEntity.setEmailVerified(true));
        return person;
    }

    public boolean delete(UUID uuid) {
        logger.info("delete person with uuid {}", uuid);
        if (repository.existsById(uuid)) {
            repository.deleteById(uuid);
            return true;
        }
        return false;
    }

    @Override
    public PersonEntity loadUserByUsername(String login) throws UsernameNotFoundException {
        PersonEntity person = findByLogin(login);
        return person;
    }
}

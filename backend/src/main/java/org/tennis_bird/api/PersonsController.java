package org.tennis_bird.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.tennis_bird.api.data.InfoConverter;
import org.tennis_bird.api.data.PersonInfoRequest;
import org.tennis_bird.api.data.PersonInfoResponse;
import org.tennis_bird.core.entities.PersonEntity;
import org.tennis_bird.core.services.PersonService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;


// TODO make base uri for all methods of this controllers = /person/
@RestController
public class PersonsController {
    @Autowired
    PersonService personService;
    @Autowired
    InfoConverter converter;
    private static final Logger logger = LogManager.getLogger(PersonsController.class.getName());

    @PostMapping(path = "/person/",
            consumes = "application/json",
            produces = "application/json")
    public PersonInfoResponse createPerson(@RequestBody PersonInfoRequest request) {
        logger.info("create person by request {}", request);
        PersonEntity person = personService.create(converter.requestToEntity(request));
        return converter.entityToResponse(person);
    }

    @PostMapping("/person/{uuid}/avatar/update/")
    public ResponseEntity<String> updateAvatar(
            @PathVariable UUID uuid,
            @RequestParam("avatar") MultipartFile avatarFile) {
        try {
            personService.updateAvatar(avatarFile, uuid);
            return ResponseEntity.ok("Avatar updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to update avatar: " + e.getMessage());
        }
    }
    
    @GetMapping(value = "/person/{uuid}/avatar/", produces = "multipart/form-data")
    public ResponseEntity<byte[]> getAvatar(@PathVariable("uuid") UUID uuid) {
        try {
            Optional<byte[]> avatarData = personService.getAvatar(uuid);
            if(avatarData.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(avatarData.get());
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.badRequest().body(null);
    }

    @GetMapping(path = "/person/",
            produces = "application/json")
    public Optional<PersonInfoResponse> getPerson(
            @RequestParam(value = "uuid") Optional<UUID> uuid,
            @RequestParam(value = "login") Optional<String> login
    ) {
        logger.info("get person by uuid {}", uuid);
        //TODO find funcs will be better in one style
        Optional<PersonEntity> personO = uuid.isPresent()
                ? personService.find(uuid.get()) : Optional.of(personService.findByLogin(login.get()));
        return personO.map(person -> converter.entityToResponse(person));
    }

    @DeleteMapping(path = "/person/{uuid}",
            produces = "application/json")
    public boolean deletePerson(@PathVariable(value = "uuid") UUID uuid) {
        logger.info("Attempting to delete person with UUID: {}", uuid);
        return personService.delete(uuid);
    }

    @PutMapping(path = "/person/{uuid}/",
            produces = "application/json")
    public Optional<PersonInfoResponse> updatePerson(
            @PathVariable(value = "uuid") UUID uuid,
            @RequestParam(value = "username") Optional<String> username,
            @RequestParam(value = "first_name") Optional<String> firstName,
            @RequestParam(value = "last_name") Optional<String> lastName,
            @RequestParam(value = "birth_date")
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            Optional<Date> birthDate,
            @RequestParam(value = "mail_address") Optional<String> mailAddress,
            @RequestParam(value = "telephone_number") Optional<String> telephoneNumber
    ) throws ParseException {
        logger.info(username);
        Optional<PersonEntity> personO = personService.find(uuid);
        if (personO.isEmpty()) {
            return Optional.empty();
        }
        PersonEntity person = personO.get();
        username.ifPresent(person::setUsername);
        firstName.ifPresent(person::setFirstName);
        lastName.ifPresent(person::setLastName);
        birthDate.ifPresent(person::setBirthDate);
        mailAddress.ifPresent(person::setMailAddress);
        telephoneNumber.ifPresent(person::setTelephoneNumber);
        Optional<PersonEntity> updatedPerson = personService.update(person);
        if (updatedPerson.isEmpty()) {
            return Optional.empty();
        }
        PersonEntity newP = updatedPerson.get();
        newP.setBirthDate(new SimpleDateFormat("yyyy-MM-dd").parse("2005-10-10"));
        return Optional.of(converter.entityToResponse(newP));
    }
}

package org.tennis_bird.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
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
        logger.info(request);
        PersonEntity person = personService.create(converter.requestToEntity(request));
        return converter.entityToResponse(person);
    }

    @GetMapping(path = "/person/{uuid}",
            produces = "application/json")
    public Optional<PersonInfoResponse> getPerson(@PathVariable(value = "uuid") UUID uuid) {
        logger.info(uuid);
        Optional<PersonEntity> personO = personService.find(uuid);
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
    public Optional<PersonInfoResponse> updatePerson (
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

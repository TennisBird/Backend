package org.tennis_bird.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tennis_bird.core.entities.PersonEntity;
import org.tennis_bird.core.services.PersonService;

import java.util.UUID;

@RestController
public class PersonsController {
    @Autowired
    PersonService personService;
    @Autowired
    PersonInfoConverter converter;
    private static final Logger logger = LogManager.getLogger(PersonsController.class.getName());
    @PostMapping(path = "/",
            consumes = "application/json",
            produces = "application/json")
    public PersonInfoResponse createPerson(@RequestBody PersonInfoRequest request) {
        logger.info(request);
        PersonEntity person = personService.create(converter.requestToEntity(request));
        return converter.entityToResponse(person);
    }

    @GetMapping(path = "/{uuid}",
            produces = "application/json")
    public PersonInfoResponse getPerson(@PathVariable(value = "uuid") UUID uuid) {
        logger.info(uuid);
        PersonEntity person = personService.find(uuid).get();
        return converter.entityToResponse(person);
    }

    @DeleteMapping(path = "/{uuid}", produces = "application/json")
    public ResponseEntity<Void> deletePerson(@PathVariable(value = "uuid") UUID uuid) {
        logger.info("Attempting to delete person with UUID: " + uuid);
        if (!personService.delete(uuid)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/{uuid}/username",
            produces = "application/json")
    public void updateUsername(
            @PathVariable(value = "uuid") UUID uuid, @RequestParam(value = "username") String username
    ) {
        logger.info(username);
        PersonEntity person = personService.find(uuid).get();
        personService.updateUsername(person, username);
    }

}

package org.tennis_bird.api;

import org.springframework.stereotype.Component;
import org.tennis_bird.core.entities.PersonEntity;

import java.util.UUID;

@Component
public class PersonInfoConverter {
    public PersonInfoResponse entityToResponse(PersonEntity personEntity) {
        return new PersonInfoResponse(
                personEntity.getUuid(),
                personEntity.getLogin(),
                personEntity.getFirstName(),
                personEntity.getLastName(),
                personEntity.getUsername(),
                personEntity.getBirthDate(),
                personEntity.getMailAddress(),
                personEntity.getTelephoneNumber());
    }

    public PersonEntity requestToEntity(PersonInfoRequest request) {
        return new PersonEntity(
                UUID.randomUUID(),
                request.getLogin(),
                request.getPassword(),
                request.getFirstName(),
                request.getLastName(),
                request.getUsername(),
                request.getBirthDate(),
                request.getMailAddress(),
                request.getTelephoneNumber());
    }
}

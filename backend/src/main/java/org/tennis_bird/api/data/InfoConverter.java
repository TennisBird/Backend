package org.tennis_bird.api.data;

import org.springframework.stereotype.Component;
import org.tennis_bird.core.entities.PersonEntity;
import org.tennis_bird.core.entities.WorkerEntity;

import java.util.UUID;


//TODO make static instead?
@Component
public class InfoConverter {
    public PersonInfoResponse entityToResponse(PersonEntity personEntity) {
        PersonInfoResponse response = new PersonInfoResponse(
                personEntity.getUuid(),
                personEntity.getLogin(),
                personEntity.getPassword(),
                personEntity.getFirstName(),
                personEntity.getLastName(),
                personEntity.getUsername(),
                personEntity.getBirthDate(),
                personEntity.getMailAddress(),
                personEntity.getTelephoneNumber());
        return response;
    }

    public WorkerInfoResponse entityToResponse(WorkerEntity workerEntity) {
        WorkerInfoResponse response = new WorkerInfoResponse(
                workerEntity.getId(),
                entityToResponse(workerEntity.getPerson()),
                workerEntity.getTeam(),
                workerEntity.getPersonRole());
        return response;
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

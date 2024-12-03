package org.tennis_bird.api.data;

import org.springframework.stereotype.Component;
import org.tennis_bird.core.entities.PersonEntity;
import org.tennis_bird.core.entities.TaskEntity;
import org.tennis_bird.core.entities.WorkerEntity;
import org.tennis_bird.core.entities.chat.ChatMemberEntity;

import java.util.UUID;


//TODO make static instead?
@Component
public class InfoConverter {
    public PersonInfoResponse entityToResponse(PersonEntity personEntity) {
        return new PersonInfoResponse(
                personEntity.getUuid(),
                personEntity.getLogin(),
                personEntity.getPassword(),
                personEntity.getFirstName(),
                personEntity.getLastName(),
                personEntity.getUsername(),
                personEntity.getBirthDate(),
                personEntity.getMailAddress(),
                personEntity.getTelephoneNumber());
    }

    public ChatMemberInfoResponse entityToResponse(ChatMemberEntity memberEntity) {
        return new ChatMemberInfoResponse(
                memberEntity.getId(),
                entityToResponse(memberEntity.getMember()),
                memberEntity.getChatEntity());
    }

    public WorkerInfoResponse entityToResponse(WorkerEntity workerEntity) {
        return new WorkerInfoResponse(
                workerEntity.getId(),
                entityToResponse(workerEntity.getPerson()),
                workerEntity.getTeam(),
                workerEntity.getPersonRole());
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
                request.getTelephoneNumber(),
                false);
    }

    public TaskEntity requestToEntity(TaskInfoRequest request) {
        return new TaskEntity(
                null,
                request.getCode(),
                request.getTitle(),
                request.getDescription(),
                request.getStatus(),
                request.getPriority(),
                request.getEstimate());
    }
}

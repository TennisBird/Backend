package org.tennis_bird.api.data;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.tennis_bird.core.entities.TeamEntity;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkerInfoResponse {
    @JsonAlias("id")
    private Long id;
    @JsonAlias("person")
    private PersonInfoResponse person;
    @JsonAlias("team")
    private TeamEntity team;
    @JsonAlias("person_role")
    private String personRole;
}

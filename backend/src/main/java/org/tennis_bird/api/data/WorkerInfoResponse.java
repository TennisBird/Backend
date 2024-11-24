package org.tennis_bird.api.data;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.tennis_bird.core.entities.TeamEntity;

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

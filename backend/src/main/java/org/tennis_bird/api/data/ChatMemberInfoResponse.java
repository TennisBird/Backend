package org.tennis_bird.api.data;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.tennis_bird.core.entities.TeamEntity;
import org.tennis_bird.core.entities.chat.ChatEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMemberInfoResponse {
    @JsonAlias("id")
    private Long id;
    @JsonAlias("person")
    private PersonInfoResponse person;
    @JsonAlias("chat")
    private ChatEntity entity;
}

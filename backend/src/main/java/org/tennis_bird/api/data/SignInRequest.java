package org.tennis_bird.api.data;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignInRequest {
    @JsonAlias
    private String email;
    @JsonAlias 
    private String password;
}

package org.tennis_bird.api;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonInfoRequest {
    @JsonAlias("login")
    private String login;
    @JsonAlias("password")
    private String password;
    @JsonAlias("first_name")
    private String firstName;
    @JsonAlias("last_name")
    private String lastName;
    @JsonAlias("username")
    private String username;
    @JsonAlias("birth_date")
    private Date birthDate;
    @JsonAlias("mail_address")
    private String mailAddress;
    @JsonAlias("telephone_number")
    private String telephoneNumber;
}

package org.tennis_bird.api.data;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;
    @JsonAlias("mail_address")
    private String mailAddress;
    @JsonAlias("telephone_number")
    private String telephoneNumber;
}

package org.tennis_bird.core.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "persons")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PersonEntity {
    @Id
    @Column(name = "uuid", nullable = false)
    private UUID uuid;
    @Column(name = "login", nullable = false)
    private String login;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "birth_date", nullable = false)
    private Date birthDate;
    @Column(name = "mail_address", nullable = false)
    private String mailAddress;
    @Column(name = "telephone_number", nullable = false)
    private String telephoneNumber;
}

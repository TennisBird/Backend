package org.tennis_bird.core.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "persons")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PersonEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "nickname", nullable = false)
    private String nickname;
    @Column(name = "birth_date", nullable = false)
    private Date birthDate;
    @Column(name = "mail_address", nullable = false)
    private String mailAddress;
}

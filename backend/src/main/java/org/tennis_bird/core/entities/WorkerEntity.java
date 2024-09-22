package org.tennis_bird.core.entities.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "workers")
@NoArgsConstructor
@AllArgsConstructor
public class WorkerEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @JoinColumn(name = "person_id", nullable = false)
    private PersonEntity person;
    @JoinColumn(name = "team_id", nullable = false)
    private TeamEntity team;
    @Column(name = "person_role", nullable = false)
    private String personRole;
}

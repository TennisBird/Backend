package org.tennis_bird.core.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "workers")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WorkerEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private PersonEntity person;
    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private TeamEntity team;
    @Column(name = "person_role", nullable = false)
    private String personRole;
}

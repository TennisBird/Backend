package org.tennis_bird.core.entities.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "worker_tasks")
@NoArgsConstructor
@AllArgsConstructor
public class WorkerTaskEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @JoinColumn(name = "person_id", nullable = false)
    private PersonEntity person;
    @JoinColumn(name = "task_id", nullable = false)
    private TaskEntity task;
    @Column(name = "worker_role", nullable = false)
    private String workerRole;
}

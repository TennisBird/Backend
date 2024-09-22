package org.tennis_bird.core.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "worker_tasks")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WorkerTaskEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "worker_id", nullable = false)
    private WorkerEntity worker;
    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private TaskEntity task;
    @Column(name = "worker_role", nullable = false)
    private String workerRole;
}

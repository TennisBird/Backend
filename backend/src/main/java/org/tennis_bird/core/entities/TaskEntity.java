package org.tennis_bird.core.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tasks")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TaskEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "code", nullable = false)
    private String code;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "description")
    private String description;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private WorkerEntity author;
    @Column(name = "status", nullable = false)
    private String status;
    @Column(name = "priority", nullable = false)
    private String priority;
    @Column(name = "estimate")
    private int estimate;
}

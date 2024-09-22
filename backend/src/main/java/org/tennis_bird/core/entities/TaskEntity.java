package org.tennis_bird.core.entities.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "tasks")
@NoArgsConstructor
@AllArgsConstructor
public class TaskEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "code", nullable = false)
    private String code;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "description")
    private String description;
    @JoinColumn(name = "author_id")
    private PersonEntity author;
    @Column(name = "status", nullable = false)
    private String status;
    @Column(name = "priority", nullable = false)
    private String priority;
    @Column(name = "estimate")
    private int estimate;
}

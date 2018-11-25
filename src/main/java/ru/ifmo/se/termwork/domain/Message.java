package ru.ifmo.se.termwork.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Domain object that represents a message between matriculant and university's worker
 */

@Entity
@Table(name = "messages")
@Data
@NamedEntityGraph(name = "messages.all", includeAllAttributes = true)
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_worker")
    private Worker worker;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_student")
    private Student student;

    @Column(name = "is_from_student")
    private boolean isFromStudent;

    private Date date;

    private String message;
}

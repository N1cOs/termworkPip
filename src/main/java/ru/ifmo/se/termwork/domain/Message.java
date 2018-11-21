package ru.ifmo.se.termwork.domain;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "messages")
@Data
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_worker")
    private Worker worker;

    @ManyToOne
    @JoinColumn(name = "id_student")
    private Student student;

    @Column(name = "is_from_student")
    private boolean isFromStudent;

    private Timestamp date;

    private String message;
}

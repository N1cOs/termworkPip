package ru.ifmo.se.termwork.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "worker_ac")
@Data
public class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @ManyToOne
    @JoinColumn(name = "id_college")
    private College college;

    private String surname;

    private String name;

    private String patronymic;

    private String email;

    private String password;

    @Column(name = "head_worker")
    private boolean isHeadWorker;

}


package ru.ifmo.se.termwork.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Speciality {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_college")
    private College college;

    private String name;

    @Column(name = "code_okso")
    private String okso;
}

package ru.ifmo.se.termwork.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class Olympiad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_subj")
    private Subject subject;

    private String name;

    private Integer level;

    @Column(name = "serial_number")
    private String serialNumber;
}

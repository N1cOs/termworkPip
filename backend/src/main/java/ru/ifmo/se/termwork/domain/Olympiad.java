package ru.ifmo.se.termwork.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Domain object that represents a student's olympiad
 */

@Data
@Entity
public class Olympiad implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_subj")
    private Subject subject;

    private String name;

    private int level;
}
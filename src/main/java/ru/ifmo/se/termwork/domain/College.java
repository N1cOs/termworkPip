package ru.ifmo.se.termwork.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
public class College {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String city;

    private String abbreviation;

    private String description;

    @OneToMany(mappedBy = "college", fetch = FetchType.EAGER)
    private Set<Worker> workers;
}

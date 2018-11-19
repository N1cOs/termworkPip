package ru.ifmo.se.termwork.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

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

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "id.speciality", fetch = FetchType.EAGER)
    private Set<Rating> ratings;
}

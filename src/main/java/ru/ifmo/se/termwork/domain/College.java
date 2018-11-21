package ru.ifmo.se.termwork.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NamedEntityGraphs({
        @NamedEntityGraph(name = "college.specialities", attributeNodes = {
                @NamedAttributeNode("specialities")
        }),
        @NamedEntityGraph(name = "college.scores", attributeNodes = {
                @NamedAttributeNode("achievementsScore")
        })
})
public class College {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String city;

    private String abbreviation;

    private String description;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "college")
    private Set<Speciality> specialities;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "id.college")
    private Set<CollegeAchievement> achievementsScore;
}

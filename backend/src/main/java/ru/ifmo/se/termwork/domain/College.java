package ru.ifmo.se.termwork.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

/**
 * Domain object which represents an university
 */

@Data
@Entity
@NamedEntityGraphs({
        @NamedEntityGraph(name = "college.specialities", attributeNodes = {
                @NamedAttributeNode("specialities")
        }),
        @NamedEntityGraph(name = "college.scores", attributeNodes = {
                @NamedAttributeNode("achievementsScore")
        }),
        @NamedEntityGraph(name = "college.all", includeAllAttributes = true)
})
public class College {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String city;

    private String abbreviation;

    private String description;

    @ToString.Exclude
    @JsonManagedReference
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "college")
    private Set<Speciality> specialities;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "id.college")
    private Set<CollegeAchievement> achievementsScore;
}

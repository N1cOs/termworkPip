package ru.ifmo.se.termwork.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
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
    @JsonView(View.Default.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonView(View.Default.class)
    private String name;

    @JsonView(View.Default.class)
    private String city;

    @JsonView(View.Default.class)
    private String abbreviation;

    @JsonView(View.Default.class)
    private String description;

    @JsonView(View.Default.class)
    private int places;

    @Column(name = "logo_url")
    @JsonView(View.Default.class)
    private String logoUrl;

    @ToString.Exclude
    @JsonManagedReference
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "college")
    @JsonView(View.Specialities.class)
    private Set<Speciality> specialities;

    @ToString.Exclude
    @JsonManagedReference
    @EqualsAndHashCode.Exclude
    @JsonView(View.Achievements.class)
    @OneToMany(mappedBy = "id.college")
    private Set<CollegeAchievement> achievementsScore;

    public static class View{

        public class Default{}

        public class Specialities extends Default{}

        public class Achievements extends Default{}
    }
}

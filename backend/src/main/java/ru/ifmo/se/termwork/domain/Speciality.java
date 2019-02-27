package ru.ifmo.se.termwork.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

/**
 * Domain object which represents a study program in the specified university
 */

@Data
@Entity
@NamedEntityGraphs({
        @NamedEntityGraph(name = "speciality.ratings", attributeNodes = {
                @NamedAttributeNode("ratings")
        }),
        @NamedEntityGraph(name = "speciality.req", attributeNodes = {
                @NamedAttributeNode("requirements")
        }),
        @NamedEntityGraph(name = "speciality.all", includeAllAttributes = true)
})
public class Speciality {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_college")
    private College college;

    private String name;

    @Column(name = "code_okso")
    private String okso;

    private int places;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "id.speciality")
    private Set<Rating> ratings;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "id.speciality")
    private Set<Requirement> requirements;
}
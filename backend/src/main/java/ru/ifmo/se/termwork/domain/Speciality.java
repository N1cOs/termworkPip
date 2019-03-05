package ru.ifmo.se.termwork.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.ifmo.se.termwork.domain.keys.RatingId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Domain object which represents a study program in the specified university
 */

@Data
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@NamedEntityGraphs({
        @NamedEntityGraph(
                name = "speciality.ratings",
                attributeNodes = {
                    @NamedAttributeNode(value = "ratings", subgraph = "ratings.id")
                },
                subgraphs = {
                    @NamedSubgraph(
                            name = "ratings.id",
                            attributeNodes = {
                                    @NamedAttributeNode(value = "id", subgraph = "rating.id.student")
                            }),
                    @NamedSubgraph(
                            name = "rating.id.student",
                            attributeNodes = {
                                    @NamedAttributeNode(value = "student", subgraph = "rating.id.student.exams")
                            }
                    ),
                    @NamedSubgraph(
                            name = "rating.id.student.exams",
                            attributeNodes = {
                                    @NamedAttributeNode(value = "achievements")
                            }
                    )
                }),
        @NamedEntityGraph(
                name = "speciality.req",
                attributeNodes = {
                    @NamedAttributeNode("requirements")
        }),
        @NamedEntityGraph(name = "speciality.all", includeAllAttributes = true)
})
public class Speciality implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "id_college")
    private College college;

    private String name;

    @Column(name = "code_okso")
    private String okso;

    private int places;

    @ToString.Exclude
    @JsonView(Rating.class)
    @JsonManagedReference
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "id.speciality")
    private Set<Rating> ratings;

    @ToString.Exclude
    @JsonManagedReference
    @EqualsAndHashCode.Exclude
    @JsonView(Requirement.class)
    @OneToMany(mappedBy = "id.speciality")
    private Set<Requirement> requirements;
}
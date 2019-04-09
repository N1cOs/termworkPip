package ru.ifmo.se.termwork.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

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
                        @NamedAttributeNode(value = "ratings", subgraph = "ratings.student")
                },
                subgraphs = {
                        @NamedSubgraph(
                                name = "ratings.student",
                                attributeNodes = {
                                        @NamedAttributeNode(value = "student", subgraph = "rating.student.exams")
                                }),
                        @NamedSubgraph(
                                name = "rating.student.exams",
                                attributeNodes = {
                                        @NamedAttributeNode(value = "exams")
                                }
                        )
                }),
        @NamedEntityGraph(
                name = "speciality.req",
                attributeNodes = {
                        @NamedAttributeNode("requirements")
                }),
        @NamedEntityGraph(name = "speciality.all", includeAllAttributes = true, attributeNodes = {
                @NamedAttributeNode(value = "ratings", subgraph = "ratings.student")
        },
                subgraphs = {
                        @NamedSubgraph(
                                name = "ratings.student",
                                attributeNodes = {
                                        @NamedAttributeNode(value = "student", subgraph = "rating.student.exams")
                                }),
                        @NamedSubgraph(
                                name = "rating.student.exams",
                                attributeNodes = {
                                        @NamedAttributeNode(value = "exams")
                                }
                        )
                }
        )
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
    @OneToMany(mappedBy = "speciality")
    private Set<Rating> ratings;

    @ToString.Exclude
    @JsonManagedReference
    @EqualsAndHashCode.Exclude
    @JsonView(Requirement.class)
    @OneToMany(mappedBy = "id.speciality")
    private Set<Requirement> requirements;
}
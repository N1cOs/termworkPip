package ru.ifmo.se.termwork.domain;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.ifmo.se.termwork.domain.keys.ExamId;
import ru.ifmo.se.termwork.domain.keys.RatingId;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Data
@Entity
@DiscriminatorValue("1")
@JsonInclude(JsonInclude.Include.NON_NULL)
@NamedEntityGraphs({
        @NamedEntityGraph(name = "student.exams", attributeNodes = {
                @NamedAttributeNode("exams")
        }),
        @NamedEntityGraph(name = "student.achievements", attributeNodes = {
                @NamedAttributeNode("achievements")
        }),
        @NamedEntityGraph(name = "student.olympiads", attributeNodes = {
                @NamedAttributeNode("olympiads")
        }),
        @NamedEntityGraph(name = "student.scores", attributeNodes = {
                @NamedAttributeNode("olympiads"),
                @NamedAttributeNode("achievements"),
                @NamedAttributeNode("exams")
        }),
        @NamedEntityGraph(name = "student.ratings", attributeNodes = {
                @NamedAttributeNode("ratings")
        }),
        @NamedEntityGraph(name = "student.examsAndRatings",
                attributeNodes = {
                    @NamedAttributeNode("exams"),
                    @NamedAttributeNode(value = "ratings", subgraph = "rating.speciality")
                },
                subgraphs = {
                        @NamedSubgraph(name = "rating.speciality",
                                attributeNodes = {
                                        @NamedAttributeNode(value = "speciality", subgraph = "rating.speciality.reqs")
                                }
                        ),
                        @NamedSubgraph(name = "rating.speciality.reqs",
                                attributeNodes = {
                                        @NamedAttributeNode(value = "requirements")
                                }
                        )
                }
        ),
        @NamedEntityGraph(name = "student.achAndRatings",
                attributeNodes = {
                        @NamedAttributeNode("achievements"),
                        @NamedAttributeNode(value = "ratings", subgraph = "rating.speciality")
                },
                subgraphs = {
                        @NamedSubgraph(name = "rating.speciality",
                                attributeNodes = {
                                        @NamedAttributeNode(value = "speciality", subgraph = "rating.speciality.college")
                                }
                        ),
                        @NamedSubgraph(name = "rating.speciality.college",
                                attributeNodes = {
                                        @NamedAttributeNode(value = "college", subgraph = "rating.speciality.college.ach")
                                }
                        ),
                        @NamedSubgraph(name = "rating.speciality.college.ach",
                                attributeNodes = {
                                        @NamedAttributeNode("achievementsScore")
                                }
                        )
                }
        ),
        @NamedEntityGraph(name = "student.scoresAndRatings",
                attributeNodes = {
                        @NamedAttributeNode("exams"),
                        @NamedAttributeNode("achievements"),
                        @NamedAttributeNode(value = "ratings", subgraph = "rating.speciality")
                },
                subgraphs = {
                        @NamedSubgraph(name = "rating.speciality",
                                attributeNodes = {
                                        @NamedAttributeNode(value = "speciality", subgraph = "rating.speciality.college")
                                }
                        ),
                        @NamedSubgraph(name = "rating.speciality.college",
                                attributeNodes = {
                                        @NamedAttributeNode(value = "requirements"),
                                        @NamedAttributeNode(value = "college", subgraph = "rating.speciality.college.ach")
                                }
                        ),
                        @NamedSubgraph(name = "rating.speciality.college.ach",
                                attributeNodes = {
                                        @NamedAttributeNode("achievementsScore")
                                }
                        )
                }
        ),
        @NamedEntityGraph(name = "student.all", includeAllAttributes = true)
})
public class Student extends User {

    @Column(name = "date_birth")
    @JsonView(User.View.Expanded.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date birthDate;

    @JsonIgnore
    @Column(name = "serial_number")
    private String serialNumber;

    @ToString.Exclude
    @JsonManagedReference
    @EqualsAndHashCode.Exclude
    @JsonView(View.Exams.class)
    @OneToMany(mappedBy = "id.student", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Exam> exams;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonView(View.Scores.class)
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
            name = "ach_student",
            joinColumns = @JoinColumn(name = "id_student"),
            inverseJoinColumns = @JoinColumn(name = "id_achievement")
    )
    private Set<Achievement> achievements;

    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonView(View.Scores.class)
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
            name = "student_olympiad",
            joinColumns = @JoinColumn(name = "id_student"),
            inverseJoinColumns = @JoinColumn(name = "id_olympiad")
    )
    private Set<Olympiad> olympiads;

    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonView(View.Ratings.class)
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Rating> ratings;

    public void addExam(Subject subject, Integer score){
        if(exams == null)
            exams = new HashSet<>();

        Exam exam = new Exam(new ExamId(this, subject), score);
        exams.add(exam);
    }

    public Rating applyFor(Speciality speciality, Olympiad olympiad, Integer priority, boolean originals){
        Optional<Rating> existRating = ratings.stream().
                filter(r -> r.getSpeciality().equals(speciality)).findFirst();
        Rating rating;
        if(existRating.isPresent()){
            rating = existRating.get();
            rating.setOlympiad(olympiad);
            rating.setPriority(priority);
            rating.setOriginals(originals);
        }
        else{
            RatingId ratingId = new RatingId(speciality.getId(), id);
            rating = Rating.builder().id(ratingId).student(this).speciality(speciality).olympiad(olympiad).
                    priority(priority).originals(originals).submissionDate(new Date()).build();
            ratings.add(rating);
        }
        return rating;
    }

    public void cancelApplication(Speciality speciality){
        ratings.removeIf(s -> s.getSpeciality().equals(speciality));
    }
}

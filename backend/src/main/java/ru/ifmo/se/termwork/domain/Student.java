package ru.ifmo.se.termwork.domain;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.ifmo.se.termwork.domain.keys.ExamId;

import javax.persistence.*;
import java.util.Date;
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
        @NamedEntityGraph(name = "student.ratings", attributeNodes = {
                @NamedAttributeNode("ratings")
        }),
        @NamedEntityGraph(name = "student.scores", attributeNodes = {
                @NamedAttributeNode("exams"),
                @NamedAttributeNode("achievements")
        }),
        @NamedEntityGraph(name = "student.forApplying", attributeNodes = {
                @NamedAttributeNode("exams"),
                @NamedAttributeNode("olympiads"),
                @NamedAttributeNode("ratings")

        })
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
    @OneToMany(mappedBy = "id.student", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Exam> exams;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
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
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Rating> ratings;

    public void addExam(Subject subject, Integer score){
        Exam exam = new Exam(new ExamId(this, subject), score);
        exams.add(exam);
    }

    public void applyFor(Speciality speciality, Olympiad olympiad, Integer priority, boolean originals){
        Optional<Rating> existRating = ratings.stream().
                filter(r -> r.getSpeciality().equals(speciality)).findFirst();
        if(existRating.isPresent()){
            Rating rating = existRating.get();
            rating.setOlympiad(olympiad);
            rating.setPriority(priority);
            rating.setOriginals(originals);
        }
        else{
            Rating rating = Rating.builder().student(this).speciality(speciality).olympiad(olympiad).
                    priority(priority).originals(originals).submissionDate(new Date()).build();
            ratings.add(rating);
        }
    }

    public void cancelApplication(Speciality speciality){
        ratings.removeIf(s -> s.getSpeciality().equals(speciality));
    }
}

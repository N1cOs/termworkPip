package ru.ifmo.se.termwork.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.ifmo.se.termwork.domain.keys.ExamId;
import ru.ifmo.se.termwork.domain.keys.RatingId;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Domain object which represents a matriculant
 */

@Entity
@Data
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
        @NamedEntityGraph(name = "student.messages", attributeNodes = {
                @NamedAttributeNode("messages")
        }),
        @NamedEntityGraph(name = "student.scores", attributeNodes = {
                @NamedAttributeNode("exams"),
                @NamedAttributeNode("achievements")
        })
})
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String surname;

    private String name;

    private String patronymic;

    @Column(name = "date_birth")
    private Date birthDate;

    private String email;

    private String phone;

    private String password;

    @Column(name = "serial_number")
    private String serialNumber;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "id.student", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Exam> exams;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "student_olympiad",
            joinColumns = @JoinColumn(name = "id_student"),
            inverseJoinColumns = @JoinColumn(name = "id_olympiad")
    )
    private Set<Olympiad> olympiads;


    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "ach_student",
            joinColumns = @JoinColumn(name = "id_student"),
            inverseJoinColumns = @JoinColumn(name = "id_achievement")
    )
    private Set<Achievement> achievements;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "id.student", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Rating> ratings;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private Set<Message> messages;

    public void addExam(Subject subject, Integer score){
        Exam exam = new Exam(new ExamId(this, subject), score);
        exams.add(exam);
    }

    public void updateExam(Subject subject, Integer score){
        exams.stream().filter(exam -> exam.getId().getSubject().equals(subject)).
                findFirst().get().setScore(score);
    }

    public void deleteExam(Subject subject){
        exams.removeIf(e -> e.getId().getSubject().equals(subject));
    }

    public void applyFor(Speciality speciality, Olympiad olympiad, Integer priority, boolean originals){
        Rating rating = Rating.builder().id(new RatingId(speciality, this)).olympiad(olympiad).
                priority(priority).originals(originals).submissionDate(new Date()).build();
        ratings.add(rating);
    }

    public void cancelApplication(Speciality speciality){
        ratings.removeIf(s -> s.getId().getSpeciality().equals(speciality));
    }

}

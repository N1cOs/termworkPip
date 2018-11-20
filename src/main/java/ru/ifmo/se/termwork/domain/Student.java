package ru.ifmo.se.termwork.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
@Data
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

    private String password;

    @Column(name = "serial_number")
    private String serialNumber;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "id.student", fetch = FetchType.EAGER)
    private Set<Exam> exams;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "student_olympiad",
            joinColumns = @JoinColumn(name = "id_student"),
            inverseJoinColumns = @JoinColumn(name = "id_olympiad")
    )
    private Set<Olympiad> olympiads;


    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "ach_student",
            joinColumns = @JoinColumn(name = "id_student"),
            inverseJoinColumns = @JoinColumn(name = "id_achievement")
    )
    private Set<Achievement> achievements;
}

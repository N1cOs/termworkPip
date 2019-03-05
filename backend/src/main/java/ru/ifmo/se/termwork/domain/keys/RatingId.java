package ru.ifmo.se.termwork.domain.keys;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ifmo.se.termwork.domain.Speciality;
import ru.ifmo.se.termwork.domain.Student;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class RatingId implements Serializable {

//    @Column(name = "id_spec")
//    private int specialityId;
//
//    @Column(name = "id_student")
//    private int studentId;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "id_spec")
    private Speciality speciality;

    @ManyToOne
    @JoinColumn(name = "id_student")
    private Student student;
}
package ru.ifmo.se.termwork.domain.keys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ifmo.se.termwork.domain.Speciality;
import ru.ifmo.se.termwork.domain.Student;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "id_spec")
    private Speciality speciality;

    @ManyToOne
    @JoinColumn(name = "id_student")
    private Student student;
}

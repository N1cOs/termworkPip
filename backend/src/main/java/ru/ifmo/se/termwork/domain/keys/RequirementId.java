package ru.ifmo.se.termwork.domain.keys;

import lombok.Data;
import ru.ifmo.se.termwork.domain.Speciality;
import ru.ifmo.se.termwork.domain.Subject;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Data
@Embeddable
public class RequirementId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "id_spec")
    private Speciality speciality;

    @ManyToOne
    @JoinColumn(name = "id_subj")
    private Subject subject;
}
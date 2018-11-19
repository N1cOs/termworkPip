package ru.ifmo.se.termwork.domain;

import lombok.Data;
import ru.ifmo.se.termwork.domain.keys.RatingId;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "speciality_student")
@Data
public class Rating {

    @EmbeddedId
    private RatingId id;

    private Integer priority;

    private boolean originals;

    @Column(name = "submission_date")
    private Date submissionDate;
}

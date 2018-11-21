package ru.ifmo.se.termwork.domain;

import lombok.Data;
import ru.ifmo.se.termwork.domain.keys.RatingId;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "speciality_student")
@Data
public class Rating {

    @EmbeddedId
    private RatingId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_olymp")
    private Olympiad olympiad;

    private Integer priority;

    private boolean originals;

    @Column(name = "submission_date")
    private Timestamp submissionDate;
}

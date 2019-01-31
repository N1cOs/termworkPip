package ru.ifmo.se.termwork.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ifmo.se.termwork.domain.keys.RatingId;

import javax.persistence.*;
import java.util.Date;

/**
 * Domain object that represents an application on certain speciality by specified student
 */

@Data
@Entity
@Table(name = "speciality_student")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rating {

    @EmbeddedId
    private RatingId id;

    @ManyToOne
    @JoinColumn(name = "id_olymp")
    private Olympiad olympiad;

    private int priority;

    private boolean originals;

    @Column(name = "submission_date")
    private Date submissionDate;
}
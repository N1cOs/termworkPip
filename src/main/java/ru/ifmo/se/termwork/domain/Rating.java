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

@Entity
@Table(name = "speciality_student")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Rating {

    @EmbeddedId
    private RatingId id;

    @ManyToOne
    @JoinColumn(name = "id_olymp")
    private Olympiad olympiad;

    private Integer priority;

    private boolean originals;

    @Column(name = "submission_date")
    private Date submissionDate;
}

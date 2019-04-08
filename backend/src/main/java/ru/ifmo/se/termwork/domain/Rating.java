package ru.ifmo.se.termwork.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "speciality_student")
public class Rating implements Comparable<Rating> {

    @JsonIgnore
    @EmbeddedId
    private RatingId id;

    @ManyToOne
    @MapsId("studentId")

    @JsonBackReference
    @JoinColumn(name = "id_student")
    private Student student;

    @ManyToOne
    @JsonIgnore
    @MapsId("specialityId")
    @JoinColumn(name = "id_spec")
    private Speciality speciality;

    @ManyToOne
    @JoinColumn(name = "id_olymp")
    private Olympiad olympiad;

    private int priority;

    private boolean originals;

    @Column(name = "submission_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm dd-MM-yyyy")
    private Date submissionDate;

    @Column(name = "total_score")
    private Integer totalScore;

    @Override
    public int compareTo(Rating o) {
        if(o.getOlympiad() != null && olympiad != null){
            return 0;
        }
        if(o.getOlympiad() != null && olympiad == null){
            return 1;
        }
        if(olympiad != null){
            return -1;
        }
        return o.getTotalScore() - totalScore;
    }
}
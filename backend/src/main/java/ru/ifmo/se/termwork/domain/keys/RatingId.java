package ru.ifmo.se.termwork.domain.keys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class RatingId implements Serializable {

    private int specialityId;

    private int studentId;
}
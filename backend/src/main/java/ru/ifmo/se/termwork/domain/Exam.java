package ru.ifmo.se.termwork.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ifmo.se.termwork.domain.keys.ExamId;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

/**
 * Domain object that represents a specified student's exam
 */

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Exam {

    @EmbeddedId
    private ExamId id;

    private int score;
}
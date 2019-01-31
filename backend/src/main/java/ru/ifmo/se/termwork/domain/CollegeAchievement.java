package ru.ifmo.se.termwork.domain;

import lombok.Data;
import ru.ifmo.se.termwork.domain.keys.CollegeAchievementId;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Domain object which represents the amount of scores that college gives for specified achievement
 */
@Data
@Entity
@Table(name = "ach_college")
public class CollegeAchievement {

    @EmbeddedId
    private CollegeAchievementId id;

    private int score;
}
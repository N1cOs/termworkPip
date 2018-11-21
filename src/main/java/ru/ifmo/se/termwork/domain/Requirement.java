package ru.ifmo.se.termwork.domain;

import lombok.Data;
import ru.ifmo.se.termwork.domain.keys.RequirementId;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "requirements")
public class Requirement {

    @EmbeddedId
    private RequirementId id;

    @Column(name = "min_score")
    private Integer minScore;

    @Column(name = "min_level_olymp_bvi")
    private Integer minLevelOfOlympiad;
}

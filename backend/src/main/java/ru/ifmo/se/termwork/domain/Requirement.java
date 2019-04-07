package ru.ifmo.se.termwork.domain;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;
import ru.ifmo.se.termwork.domain.keys.RequirementId;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Domain object which represents requirements on specified speciality (e.g. minimum score or
 * minimum level of olympiad by certain subject)
 */

@Entity
@Data
@Table(name = "requirement")
public class Requirement {

    @EmbeddedId
    @JsonUnwrapped
    private RequirementId id;

    @Column(name = "min_score")
    private Integer minScore;

    @Column(name = "min_level_olymp_bvi")
    private Integer minLevelOfOlympiad;
}

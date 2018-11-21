package ru.ifmo.se.termwork.domain.keys;

import lombok.Data;
import ru.ifmo.se.termwork.domain.Achievement;
import ru.ifmo.se.termwork.domain.College;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
@Data
public class CollegeAchievementId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "id_college")
    private College college;

    @ManyToOne
    @JoinColumn(name = "id_achievement")
    private Achievement achievement;
}

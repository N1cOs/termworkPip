package ru.ifmo.se.termwork.domain;

import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Entity
@DiscriminatorValue("2")
public class Worker extends User {

    @ManyToOne
    @JoinColumn(name = "id_college")
    private College college;
}

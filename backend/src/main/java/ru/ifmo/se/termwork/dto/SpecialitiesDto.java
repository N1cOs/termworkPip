package ru.ifmo.se.termwork.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ifmo.se.termwork.domain.Speciality;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpecialitiesDto {

    private int amount;

    private Collection<Speciality> specialities;
}

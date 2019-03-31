package ru.ifmo.se.termwork.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ifmo.se.termwork.domain.College;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollegesDto {

    private int amount;

    private Collection<College> colleges;
}

package ru.ifmo.se.termwork.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class ClaimDto {

    private int specialityId;

    @Min(value = 1)
    @Max(value = 3)
    private int priority;

    private boolean originals;

    private int olympiadId = -1;
}

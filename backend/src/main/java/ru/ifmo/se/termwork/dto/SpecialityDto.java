package ru.ifmo.se.termwork.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SpecialityDto {

    private int id;

    private String okso;

    private CollegeDto college;
}

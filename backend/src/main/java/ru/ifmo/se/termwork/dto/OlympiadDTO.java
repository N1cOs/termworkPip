package ru.ifmo.se.termwork.dto;

import lombok.Data;

@Data
public class OlympiadDTO {

    private int id;

    private String subjectName;
    private int subjectId;

    private String name;

    private int level;

    private String serialNumber;
}

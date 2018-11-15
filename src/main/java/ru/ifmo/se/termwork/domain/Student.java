package ru.ifmo.se.termwork.domain;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String surname;

    private String name;

    private String patronymic;

    @Column(name = "date_birth")
    private Date birthDate;

    private String email;

    private String password;

    @Column(name = "serial_number")
    private String serialNumber;

}

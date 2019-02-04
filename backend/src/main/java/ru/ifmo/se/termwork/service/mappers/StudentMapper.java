package ru.ifmo.se.termwork.service.mappers;

import lombok.NoArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.ifmo.se.termwork.domain.*;
import ru.ifmo.se.termwork.dto.ExamDto;
import ru.ifmo.se.termwork.dto.OlympiadDto;
import ru.ifmo.se.termwork.dto.StudentDto;
import ru.ifmo.se.termwork.repository.AchievementRepository;
import ru.ifmo.se.termwork.repository.OlympiadRepository;
import ru.ifmo.se.termwork.repository.StudentRepository;

import java.util.*;


@Mapper(componentModel = "spring")
public abstract class StudentMapper {

    //todo: inject olympiad-, exam-, achievement- Repos.
    @Autowired
    OlympiadRepository olympiadRepository;

    @Mappings({
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "surname", target = "surname"),
            @Mapping(source = "patronymic", target = "patronymic"),
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "phone", target = "phone"),
            @Mapping(source = "password", target = "password"),
            @Mapping(source = "serialNumber", target = "serialNumber"),
            @Mapping(target = "exams"),
            @Mapping(target = "achievements"),
            @Mapping(target = "olympiads"),
    })
    public abstract Student studentDtoToStudent(StudentDto studentDto);


    public Set<Exam> formExamSet(List<ExamDto> examDtos) {
        Set<Exam> exams = new HashSet<>();
//        studentDto.getExams().forEach(
//                examDto -> {
//
//                }
//        );
        return exams;
    }

    public Set<Olympiad> formOlympiadsSet(List<OlympiadDto> olympiadDtos) {
        Set<Olympiad> olympiads = new HashSet<>();
        olympiadDtos.forEach(
                olympiadDto -> {
                    System.out.println(olympiadDto);
                    System.out.println(olympiadRepository.findBySerialNumber(olympiadDto.getSerialNumber()));
                    Optional<Olympiad> olympiad =
                            olympiadRepository.findBySerialNumber(olympiadDto.getSerialNumber());
                    olympiads.add(olympiad.get());
                }
        );
        return olympiads;
    }

    public Set<Achievement> formAchievement (List<String> achievements){
        return new HashSet<>();
    }

}

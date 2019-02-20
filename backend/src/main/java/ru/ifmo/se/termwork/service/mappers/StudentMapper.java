package ru.ifmo.se.termwork.service.mappers;

import org.mapstruct.*;

import ru.ifmo.se.termwork.domain.Achievement;
import ru.ifmo.se.termwork.domain.Olympiad;
import ru.ifmo.se.termwork.domain.Student;
import ru.ifmo.se.termwork.domain.Subject;
import ru.ifmo.se.termwork.dto.ExamDto;
import ru.ifmo.se.termwork.dto.StudentDto;
import ru.ifmo.se.termwork.repository.AchievementRepository;
import ru.ifmo.se.termwork.repository.OlympiadRepository;
import ru.ifmo.se.termwork.repository.SubjectRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Mapper
public interface StudentMapper {

    @Mappings({
            @Mapping(target = "exams", ignore = true),
            @Mapping(target = "achievements", source = "achievementsId", qualifiedByName = "getAchievements"),
            @Mapping(target = "olympiads", source = "olympiadsId", qualifiedByName = "getOlympiads")
    })
    Student toStudent(StudentDto studentDto,
                      @Context SubjectRepository sr,
                      @Context AchievementRepository ar,
                      @Context OlympiadRepository or);

    @AfterMapping
    default void setExams(@MappingTarget Student student, StudentDto studentDto,
                          @Context SubjectRepository subjectRepository) {
        List<ExamDto> examDtoList = studentDto.getExams();
        List<Subject> subjects = subjectRepository.findAllById(examDtoList.stream().
                map(ExamDto::getSubjectId).collect(Collectors.toList()));
        for (Subject subject : subjects) {
            int score = -1;
            for (ExamDto examDto : examDtoList) {
                if (examDto.getSubjectId() == subject.getId()) {
                    score = examDto.getScore();
                    break;
                }
            }
            student.addExam(subject, score);
        }
    }

    @Named("getAchievements")
    default Set<Achievement> map(List<Integer> achievementsId,
                                 @Context AchievementRepository achievementRepository) {
        return new HashSet<>(achievementRepository.findAllById(achievementsId));
    }

    @Named("getOlympiads")
    default Set<Olympiad> map(List<Integer> olympiadsId,
                              @Context OlympiadRepository olympiadRepository) {
        return new HashSet<>(olympiadRepository.findAllById(olympiadsId));
    }

}

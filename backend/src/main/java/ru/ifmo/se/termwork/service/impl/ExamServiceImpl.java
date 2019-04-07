package ru.ifmo.se.termwork.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.ifmo.se.termwork.domain.*;
import ru.ifmo.se.termwork.domain.keys.ExamId;
import ru.ifmo.se.termwork.dto.ExamDto;
import ru.ifmo.se.termwork.repository.ExamRepository;
import ru.ifmo.se.termwork.repository.StudentRepository;
import ru.ifmo.se.termwork.repository.SubjectRepository;
import ru.ifmo.se.termwork.service.ExamService;
import ru.ifmo.se.termwork.support.exception.ApiException;
import ru.ifmo.se.termwork.support.exception.ClientException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExamServiceImpl implements ExamService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private ExamRepository examRepository;

    @Override
    public void saveExams(int studentId, List<ExamDto> exams) {
        List<Exam> examsEntity = getExams(studentId, exams);
        examRepository.saveAll(examsEntity);
    }

    @Override
    public void deleteExams(int studentId, List<Integer> exams) {
        Student student = studentRepository.findWithExamsAndRatingsById(studentId).
                orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "exception.user.notFound"));
        for(Integer subjectId : exams){
            for(Rating rating : student.getRatings()){
                Optional<Requirement> requirement = rating.getSpeciality().getRequirements().stream().
                        filter(r -> r.getId().getSubject().getId() == subjectId).findFirst();
                if(requirement.isPresent()){
                    String subjectName = requirement.get().getId().getSubject().getName();
                    String specialityName = requirement.get().getId().getSpeciality().getOkso();
                    throw new ClientException(HttpStatus.BAD_REQUEST, "exception.exam.used", subjectName, specialityName);
                }
            }
            student.getExams().removeIf(e -> e.getId().getSubject().getId() == subjectId);
        }
        studentRepository.save(student);
    }

    private List<Exam> getExams(int studentId, List<ExamDto> exams){
        Student student = studentRepository.findById(studentId).
                orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "exception.user.notFound"));
        List<Subject> subjects = subjectRepository.findAll();
        ArrayList<Exam> examsEntity = new ArrayList<>();

        for(ExamDto examDto : exams){
            Subject subject = null;
            for(Subject s : subjects){
                if(s.getId() == examDto.getSubjectId())
                    subject = s;
            }
            if(subject == null)
                throw new ApiException(HttpStatus.BAD_REQUEST, "exception.subject.invalidId",
                        examDto.getSubjectId());

            ExamId examId = new ExamId(student, subject);
            examsEntity.add(new Exam(examId, examDto.getScore()));
        }
        return examsEntity;
    }
}

package ru.ifmo.se.termwork.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ifmo.se.termwork.domain.Exam;
import ru.ifmo.se.termwork.domain.Student;
import ru.ifmo.se.termwork.domain.Subject;
import ru.ifmo.se.termwork.domain.keys.ExamId;
import ru.ifmo.se.termwork.dto.ExamDto;
import ru.ifmo.se.termwork.repository.ExamRepository;
import ru.ifmo.se.termwork.repository.StudentRepository;
import ru.ifmo.se.termwork.repository.SubjectRepository;
import ru.ifmo.se.termwork.service.ExamService;
import ru.ifmo.se.termwork.service.MessageService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExamServiceImpl implements ExamService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private MessageService messageService;

    @Override
    public void saveExams(int studentId, List<ExamDto> exams) {
        List<Exam> examsEntity = getExams(studentId, exams);
        examRepository.saveAll(examsEntity);
    }

    @Override
    public void deleteExams(int studentId, List<ExamDto> exams) {
        List<Exam> studentExams = getExams(studentId, exams);
        examRepository.deleteAll(studentExams);
    }

    private List<Exam> getExams(int studentId, List<ExamDto> exams){
        Student student = studentRepository.findById(studentId).
                orElseThrow(() -> new IllegalArgumentException(messageService.getMessage("exception.userNotFound")));
        List<Subject> subjects = subjectRepository.findAll();
        ArrayList<Exam> examsEntity = new ArrayList<>();

        for(ExamDto examDto : exams){
            Subject subject = null;
            for(Subject s : subjects){
                if(s.getId() == examDto.getSubjectId())
                    subject = s;
            }
            if(subject == null)
                throw new IllegalArgumentException(messageService.getMessage("exception.subject.invalidId",
                        new Object[]{examDto.getSubjectId()}));

            ExamId examId = new ExamId(student, subject);
            examsEntity.add(new Exam(examId, examDto.getScore()));
        }
        return examsEntity;
    }
}

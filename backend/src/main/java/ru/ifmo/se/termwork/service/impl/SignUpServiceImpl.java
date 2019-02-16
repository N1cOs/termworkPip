package ru.ifmo.se.termwork.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ifmo.se.termwork.domain.Student;
import ru.ifmo.se.termwork.dto.StudentDto;
import ru.ifmo.se.termwork.repository.AchievementRepository;
import ru.ifmo.se.termwork.repository.OlympiadRepository;
import ru.ifmo.se.termwork.repository.StudentRepository;
import ru.ifmo.se.termwork.repository.SubjectRepository;
import ru.ifmo.se.termwork.service.SignUpService;
import ru.ifmo.se.termwork.service.mappers.StudentMapper;

@Service
public class SignUpServiceImpl implements SignUpService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private OlympiadRepository olympiadRepository;

    @Autowired
    private AchievementRepository achievementRepository;

    @Override
    public boolean isExists(String serialNumber) {
        return studentRepository.findBySerialNumber(serialNumber).isPresent();
    }

    @Override
    public void signUp(StudentDto studentDto) {
        Student student = studentMapper.
                toStudent(studentDto, subjectRepository, achievementRepository, olympiadRepository);
        studentRepository.save(student);
    }
}

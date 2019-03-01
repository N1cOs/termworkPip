package ru.ifmo.se.termwork.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.ifmo.se.termwork.domain.Olympiad;
import ru.ifmo.se.termwork.domain.Speciality;
import ru.ifmo.se.termwork.domain.Student;
import ru.ifmo.se.termwork.repository.OlympiadRepository;
import ru.ifmo.se.termwork.repository.SpecialityRepository;
import ru.ifmo.se.termwork.repository.StudentRepository;
import ru.ifmo.se.termwork.service.ApplyingService;
import ru.ifmo.se.termwork.support.exception.ApiException;

@Service
@RequiredArgsConstructor
public class ApplyingServiceImpl implements ApplyingService {

    private final StudentRepository studentRepository;

    private final SpecialityRepository specialityRepository;

    private final OlympiadRepository olympiadRepository;

    @Override
    public void applyForSpeciality(int studentId, int specialityId, int olympiadId) {
        Student student = studentRepository.findWithRatingsById(studentId).
                orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "exception.user.notFound"));
        Speciality speciality = specialityRepository.findWithAllById(specialityId).
                orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "exception.speciality.notFound",
                        new Object[]{specialityId}));
        Olympiad olympiad = olympiadRepository.findById(olympiadId).
                orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "exception.olympiad.notFound",
                        new Object[]{olympiadId}));

    }
}

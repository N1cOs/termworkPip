package ru.ifmo.se.termwork.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.ifmo.se.termwork.domain.*;
import ru.ifmo.se.termwork.dto.RatingDto;
import ru.ifmo.se.termwork.dto.RatingsDto;
import ru.ifmo.se.termwork.dto.StudentResponseDto;
import ru.ifmo.se.termwork.repository.SpecialityRepository;
import ru.ifmo.se.termwork.service.RatingService;
import ru.ifmo.se.termwork.support.exception.ApiException;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private SpecialityRepository specialityRepository;

    public RatingServiceImpl() {
    }

    @Override
    public RatingsDto getRatings(int specialityId) {
        Speciality speciality = specialityRepository.findWithAllById(specialityId).orElseThrow(() ->
                new ApiException(HttpStatus.BAD_REQUEST, "exception.speciality.notFound", specialityId));


        TreeSet<Rating> ratings = new TreeSet<>(speciality.getRatings());
        HashSet<RatingDto> olympiads = new HashSet<>();
        HashSet<RatingDto> exams = new HashSet<>();

        int place = 1;
        int placeOriginal = 1;
        for(Rating rating : ratings){
            Student student = rating.getStudent();
            RatingDto ratingDto = new RatingDto();

            StudentResponseDto studentResponseDto = getStudentDto(student);
            if(rating.getOlympiad() != null){
                ratingDto.setStudent(studentResponseDto);
                ratingDto.setSubmissionDate(rating.getSubmissionDate());
                ratingDto.setSuccess(true);
                olympiads.add(ratingDto);

                place++;
                placeOriginal++;
            }
            else{
                studentResponseDto.setExams(getReqExams(speciality, student));
                ratingDto.setStudent(studentResponseDto);
                ratingDto.setSubmissionDate(rating.getSubmissionDate());
                ratingDto.setTotalScore(rating.getTotalScore());
                ratingDto.setPlace(place);
                ratingDto.setPlaceOriginal(placeOriginal);
                ratingDto.setOriginals(rating.isOriginals());
                ratingDto.setSuccess(speciality.getPlaces() >= placeOriginal);
                exams.add(ratingDto);

                if(rating.isOriginals()){
                    placeOriginal++;
                }
                place++;
            }
        }
        return new RatingsDto(olympiads, exams);
    }

    private StudentResponseDto getStudentDto(Student student){
        StudentResponseDto studentResponseDto = new StudentResponseDto();
        studentResponseDto.setName(student.getName());
        studentResponseDto.setSurname(student.getSurname());
        studentResponseDto.setPatronymic(student.getPatronymic());
        return studentResponseDto;
    }

    private Set<Exam> getReqExams(Speciality speciality, Student student){
        HashSet<Exam> exams = new HashSet<>();
        for (Requirement requirement : speciality.getRequirements()){
            Exam exam = student.getExams().stream().filter(e -> e.getId().getSubject().getId() ==
                    requirement.getId().getSubject().getId()).findFirst().get();
            exams.add(exam);
        }
        return exams;
    }
}

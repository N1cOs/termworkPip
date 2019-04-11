package ru.ifmo.se.termwork.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.ifmo.se.termwork.domain.Rating;
import ru.ifmo.se.termwork.domain.Speciality;
import ru.ifmo.se.termwork.domain.Student;
import ru.ifmo.se.termwork.dto.CollegeDto;
import ru.ifmo.se.termwork.dto.RatingDto;
import ru.ifmo.se.termwork.dto.SpecialityDto;
import ru.ifmo.se.termwork.dto.StudentResponseDto;
import ru.ifmo.se.termwork.repository.SpecialityRepository;
import ru.ifmo.se.termwork.repository.StudentRepository;
import ru.ifmo.se.termwork.service.StudentService;
import ru.ifmo.se.termwork.service.mappers.StudentMapper;
import ru.ifmo.se.termwork.support.exception.ApiException;

import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SpecialityRepository specialityRepository;

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public StudentResponseDto getStudentDto(int studentId) {
        Student student = studentRepository.findWithScoresAndRatingsById(studentId).
                orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "exception.user.notFound"));
        StudentResponseDto studentResponse = studentMapper.toStudentResponse(student);
        if (student.getRatings().size() > 0) {
            List<Speciality> specialities = specialityRepository.findWithRatingsAllById(student.getRatings().stream().
                    map(r -> r.getSpeciality().getId()).collect(Collectors.toList()));

            HashSet<RatingDto> ratings = new HashSet<>();
            for(Rating rating : student.getRatings()){
                RatingDto dto = new RatingDto();
                dto.setTotalScore(rating.getTotalScore());
                dto.setOriginals(rating.isOriginals());
                dto.setIsOlympiad(rating.getOlympiad() != null);

                CollegeDto collegeDto = new CollegeDto(rating.getSpeciality().getCollege().getId(),
                        rating.getSpeciality().getCollege().getAbbreviation());
                SpecialityDto specialityDto = new SpecialityDto(rating.getSpeciality().getId(),
                        rating.getSpeciality().getOkso(), collegeDto);
                dto.setSpeciality(specialityDto);

                Speciality speciality = specialities.stream().filter(s -> s.getId() == rating.getSpeciality().getId()).
                        findFirst().get();
                TreeSet<Rating> sortedRatings = new TreeSet<>(speciality.getRatings());

                int place = 1;
                int placeOriginal = 1;
                for(Rating r : sortedRatings){
                    if(r.getStudent().getId() == studentId){
                        break;
                    }
                    if(r.isOriginals()){
                        placeOriginal++;
                    }
                    place++;
                }
                dto.setPlace(place);
                dto.setPlaceOriginal(placeOriginal);
                dto.setSuccess(speciality.getPlaces() >= placeOriginal);
                ratings.add(dto);
            }
            studentResponse.setRatings(ratings);
        }
        return studentResponse;
    }
}

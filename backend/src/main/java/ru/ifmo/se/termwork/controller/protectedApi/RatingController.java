package ru.ifmo.se.termwork.controller.protectedApi;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.se.termwork.domain.Rating;
import ru.ifmo.se.termwork.domain.Speciality;
import ru.ifmo.se.termwork.domain.User;
import ru.ifmo.se.termwork.repository.SpecialityRepository;
import ru.ifmo.se.termwork.support.exception.ApiException;

import java.util.Set;

@RestController
@RequestMapping("public/ratings")
public class RatingController {

    @Autowired
    private SpecialityRepository specialityRepository;

    @GetMapping("/{id}")
    @JsonView(User.View.Exams.class)
    public Set<Rating> getRatings(@PathVariable("id") Integer specialityId){
        Speciality speciality = specialityRepository.findWithRatingsById(specialityId).orElseThrow(() ->
                new ApiException(HttpStatus.BAD_REQUEST, "exception.speciality.notFound", specialityId));
        return speciality.getRatings();
    }
}

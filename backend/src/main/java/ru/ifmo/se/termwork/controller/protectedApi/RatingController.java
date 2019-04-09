package ru.ifmo.se.termwork.controller.protectedApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.se.termwork.dto.RatingsDto;
import ru.ifmo.se.termwork.service.RatingService;

@RestController
@RequestMapping("public/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @GetMapping("/{id}")
    public RatingsDto getRatings(@PathVariable("id") Integer specialityId){
        return ratingService.getRatings(specialityId);
    }
}

package ru.ifmo.se.termwork.controller.publicApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.se.termwork.domain.College;
import ru.ifmo.se.termwork.repository.CollegeRepository;
import ru.ifmo.se.termwork.support.exception.ApiException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/public/colleges")
public class CollegeController {


    @Autowired
    private CollegeRepository collegeRepository;

    @GetMapping
    public List<College> getAllColleges(@RequestParam(value = "limit", defaultValue = "20") Integer limit,
                                        @RequestParam(value = "offset", defaultValue = "0") Integer offset){
        if(limit < 0 || offset < 0)
            throw new ApiException(HttpStatus.BAD_REQUEST, "exception.limit.offset.negative");
        return collegeRepository.findAllByOrderById().stream().
                skip(offset).limit(limit).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    //ToDo: add Json views
    public College getCollege(@PathVariable("id") Integer collegeId){
        return collegeRepository.findWithAllById(collegeId).orElseThrow(() ->
                new ApiException(HttpStatus.BAD_REQUEST, "exception.college.notFound", collegeId));
    }
}

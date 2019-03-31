package ru.ifmo.se.termwork.controller.publicApi;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.se.termwork.domain.College;
import ru.ifmo.se.termwork.dto.CollegesDto;
import ru.ifmo.se.termwork.repository.CollegeRepository;
import ru.ifmo.se.termwork.support.exception.ApiException;
import ru.ifmo.se.termwork.support.exception.Error;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/public/colleges")
public class CollegeController {

    private final static String SPECIALITIES_FIELD = "specialities";
    private final static String ACH_FIELD = "achievements";

    @Autowired
    private CollegeRepository collegeRepository;

    @JsonView(College.View.Default.class)
    @ApiOperation(value = "Get colleges", notes = "Get colleges with limit and offset options")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Limit and offset can't be negative", response = Error.class)
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollegesDto getAllColleges(@RequestParam(value = "limit", defaultValue = "20") Integer limit,
                                      @RequestParam(value = "offset", defaultValue = "0") Integer offset){
        if(limit < 0 || offset < 0)
            throw new ApiException(HttpStatus.BAD_REQUEST, "exception.limit.offset.negative");
        List<College> colleges = collegeRepository.findAllByOrderById();
        return new CollegesDto(colleges.size(),
                colleges.stream().skip(offset).limit(limit).collect(Collectors.toList()));
    }

    @JsonView(College.View.Achievements.class)
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get college", notes = "Get college by specified id with achievements score. If" +
            " you want to get specialities, see SpecialityController")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "College with specified id does not exist", response = Error.class)
    })
    public College getCollege(@PathVariable("id") Integer collegeId) {
        return collegeRepository.findWithScoresById(collegeId).orElseThrow(() ->
                new ApiException(HttpStatus.BAD_REQUEST, "exception.college.notFound", collegeId));
    }
}

package ru.ifmo.se.termwork.controller.publicApi;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.se.termwork.domain.College;
import ru.ifmo.se.termwork.repository.CollegeRepository;
import ru.ifmo.se.termwork.support.exception.ApiException;
import ru.ifmo.se.termwork.support.exception.Error;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/public/colleges")
public class CollegeController {

    private final static String SPECIALITIES_FIELD = "specialities";
    private final static String ACH_FIELD = "achievements";

    @Autowired
    private CollegeRepository collegeRepository;

    @ApiOperation(value = "Get colleges", notes = "Get colleges with limit and offset options")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Limit and offset can't be negative", response = Error.class)
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<College> getAllColleges(@RequestParam(value = "limit", defaultValue = "20") Integer limit,
                                        @RequestParam(value = "offset", defaultValue = "0") Integer offset){
        if(limit < 0 || offset < 0)
            throw new ApiException(HttpStatus.BAD_REQUEST, "exception.limit.offset.negative");
        return collegeRepository.findAllByOrderById().stream().
                skip(offset).limit(limit).collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get college", notes = "Get college by specified id with specified fields. Specialities return " +
            "without ratings and requirements, for these purposes use rating or speciality controller")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "College with specified id does not exist")
    })
    public College getCollege(@PathVariable("id") Integer collegeId,
                              @ApiParam(value = "Allowable values: specialities, achievements")
                              @RequestParam(value = "fields", required = false) List<String> fields) {
        Supplier<ApiException> exceptionSupplier = () ->
                new ApiException(HttpStatus.BAD_REQUEST, "exception.college.notFound", collegeId);

        if (fields != null) {
            if(fields.contains(SPECIALITIES_FIELD)){
                if(fields.contains(ACH_FIELD)){
                    return collegeRepository.findWithAllById(collegeId).orElseThrow(exceptionSupplier);
                }
                return collegeRepository.findWithSpecialitiesById(collegeId).orElseThrow(exceptionSupplier);
            }
            else if(fields.contains(ACH_FIELD))
                return collegeRepository.findWithScoresById(collegeId).orElseThrow(exceptionSupplier);
        }
        return collegeRepository.findById(collegeId).orElseThrow(exceptionSupplier);
    }
}

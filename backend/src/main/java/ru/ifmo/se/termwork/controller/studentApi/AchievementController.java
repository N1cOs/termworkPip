package ru.ifmo.se.termwork.controller.studentApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.se.termwork.domain.Achievement;
import ru.ifmo.se.termwork.domain.Student;
import ru.ifmo.se.termwork.domain.User;
import ru.ifmo.se.termwork.repository.AchievementRepository;
import ru.ifmo.se.termwork.repository.StudentRepository;
import ru.ifmo.se.termwork.support.annotation.RecomputeScore;
import ru.ifmo.se.termwork.support.exception.ApiException;

import java.util.List;

@RestController
@RequestMapping("/student/achievements")
@RecomputeScore(RecomputeScore.Type.ALL)
public class AchievementController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AchievementRepository achievementRepository;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveAchievements(@AuthenticationPrincipal User user,
                                    @RequestBody List<Integer> achievementsId){
        Student student = studentRepository.findWithAchievementsById(user.getId()).
                orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "exception.user.notFound"));
        List<Achievement> achievements = achievementRepository.findAllById(achievementsId);
        if(achievementsId.size() != achievements.size()){
            throw new ApiException(HttpStatus.BAD_REQUEST, "exception.achs.notFound");
        }
        achievements.forEach(a -> student.getAchievements().add(a));
        studentRepository.save(student);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteAchievements(@AuthenticationPrincipal User user,
                                             @RequestBody List<Integer> achievements){
        Student student = studentRepository.findWithAchievementsById(user.getId()).
                orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "exception.user.notFound"));
        for(int achId : achievements){
            student.getAchievements().removeIf(a -> a.getId() == achId);
        }
        studentRepository.save(student);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

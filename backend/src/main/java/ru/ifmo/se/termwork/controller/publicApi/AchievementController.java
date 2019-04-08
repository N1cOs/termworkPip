package ru.ifmo.se.termwork.controller.publicApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.se.termwork.domain.Achievement;
import ru.ifmo.se.termwork.repository.AchievementRepository;

import java.util.Collection;

@RestController("publicAchievementController")
@RequestMapping("/public/achievements")
public class AchievementController {

    @Autowired
    private AchievementRepository achievementRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Achievement> getAchievements(@RequestParam(value = "collegeId", required = false) Integer collegeId){
        return achievementRepository.findAll();
    }
}

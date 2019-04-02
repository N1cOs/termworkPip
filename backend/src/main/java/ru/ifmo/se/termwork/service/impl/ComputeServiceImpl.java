package ru.ifmo.se.termwork.service.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.ifmo.se.termwork.domain.*;
import ru.ifmo.se.termwork.repository.CollegeRepository;
import ru.ifmo.se.termwork.repository.StudentRepository;
import ru.ifmo.se.termwork.service.ComputeService;

@Log4j
@Service
public class ComputeServiceImpl implements ComputeService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CollegeRepository collegeRepository;

    @Async
    @Override
    public void computeScoreAndSaveAsync(Student student, Rating rating) {
        /*College without scores, so we have to fetch it*/
        College college = collegeRepository.findWithScoresById(rating.getSpeciality().getCollege().getId()).
                orElseThrow(IllegalArgumentException::new);

        int totalScore = getExamScore(student) + getAchievementsScore(student, college);
        rating.setTotalScore(totalScore);
        try{
            studentRepository.save(student);
        } catch (RuntimeException e){
            log.error("Saving student", e);
        }
    }

    @Override
    public Student recomputeExams(int studentId) {
        Student student = studentRepository.findWithExamsAndRatingsById(studentId).
                orElseThrow(IllegalArgumentException::new);
        int score = getExamScore(student);
        for(Rating rating : student.getRatings())
            rating.setTotalScore(score);
        return student;
    }

    @Override
    public Student recomputeAchievements(int studentId) {
        Student student = studentRepository.findWithAchievementsAndRatingsById(studentId).
                orElseThrow(IllegalArgumentException::new);

        for(Rating rating : student.getRatings()){
            int score = getAchievementsScore(student, rating.getSpeciality().getCollege());
            rating.setTotalScore(score);
        }

        return student;
    }

    @Override
    public Student recomputeAll(int studentId) {
        Student student = studentRepository.findWithScoresAndRatingsById(studentId).
                orElseThrow(IllegalArgumentException::new);
        int examScore = getExamScore(student);

        for(Rating rating : student.getRatings()){
            College college = rating.getSpeciality().getCollege();
            int totalScore = examScore + getAchievementsScore(student, college);
            rating.setTotalScore(totalScore);
        }

        return student;
    }

    private int getExamScore(Student student){
        return student.getExams().stream().mapToInt(Exam::getScore).sum();
    }

    private int getAchievementsScore(Student student, College college){
        int totalScore = 0;

        for(Achievement achievement : student.getAchievements()){
            for(CollegeAchievement collegeAchievement : college.getAchievementsScore()){
                if(collegeAchievement.getId().getAchievement().equals(achievement)){
                    totalScore += collegeAchievement.getScore();
                    break;
                }
            }
        }

        return totalScore;
    }
}

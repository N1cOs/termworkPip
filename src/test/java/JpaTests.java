import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.ifmo.se.termwork.config.JpaConfig;
import ru.ifmo.se.termwork.domain.*;
import ru.ifmo.se.termwork.domain.keys.ExamId;
import ru.ifmo.se.termwork.repository.*;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.Set;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {JpaConfig.class})
public class JpaTests {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CollegeRepository collegeRepository;

    @Autowired
    private SpecialityRepository specialityRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private OlympiadRepository olympiadRepository;

    @Autowired
    private AchievementRepository achievementRepository;

    @Test
    public void testStudent(){
        Student student = studentRepository.findById(1).get();

        Student newStudent = new Student();
        newStudent.setName("Julia");
        newStudent.setSurname("Ivanova");
        newStudent.setSurname("Vladimirovna");
        newStudent.setBirthDate(Date.valueOf("1999-05-07"));
        newStudent.setEmail("ivanova@gmai.com");
        newStudent.setSerialNumber("4040 546908");
        newStudent.setPassword("Juli1234");
        studentRepository.save(newStudent);
    }

    @Test
    public void testCollege(){
        College itmo = collegeRepository.findById(1).get();
        System.out.println(itmo.getSpecialities());
        System.out.println(itmo.getName());

        College msu = new College();
        msu.setName("Moscow State University");
        msu.setAbbreviation("MSU");
        msu.setCity("Moscow");
        msu.setDescription("lo lo la lo");
        collegeRepository.save(msu);
    }

    @Test
    public void testSpeciality(){
        Speciality speciality = new Speciality();
        speciality.setCollege(collegeRepository.getOne(1));
        speciality.setName("KT");
        speciality.setOkso("09.03.00");
        specialityRepository.save(speciality);

        System.out.println(specialityRepository.findById(1).get());

    }

    @Test
    public void testExams(){
        Subject math = subjectRepository.findByNameIgnoreCase("math");
        Student student = studentRepository.findStudentWithScoresById(3);
        Exam exam = new Exam(new ExamId(student, math), 60);
        student.getExams().add(exam);
        System.out.println("...saving");
        studentRepository.save(student);
//        studentRepository.addExam(2, english.getId(), 100);
//        studentRepository.updateExam(2, english.getId(), 80);
//        Student student = studentRepository.findById(2).get();
//        student.getExams().forEach(n -> System.out.println(n.getScore()));
    }


    @Test
    @Transactional
    public void testOlympiad(){
        studentRepository.addOlympiad(4, 7);
    }

    @Test
    public void testRating(){
        Speciality vt = specialityRepository.findById(1).get();
        Set<Rating> ratings = vt.getRatings();
        ratings.forEach(n -> System.out.println(n.getOlympiad()));
    }

    @Test
    public void testAch(){
        Student student = studentRepository.findById(5).get();
        Achievement achievement = achievementRepository.findById(1).get();
        student.getAchievements().add(achievement);

    }

    @Test
    public void testReqs(){
        Speciality vt = specialityRepository.findWithReqsById(1);
        System.out.println(vt.getRequirements());
    }


}
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.ifmo.se.termwork.config.JpaConfig;
import ru.ifmo.se.termwork.domain.*;
import ru.ifmo.se.termwork.repository.*;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {JpaConfig.class})
@Log4j
public class JpaTests {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private WorkerRepository workerRepository;

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

    @Autowired
    private MessageRepository messageRepository;

    @Test
    public void testStudent(){
        Student student = studentRepository.
                findByEmailOrPhone(null, "+79112938535");
        System.out.println(student);
//        Student newStudent = new Student();
//        newStudent.setName("Julia");
//        newStudent.setSurname("Ivanova");
//        newStudent.setSurname("Vladimirovna");
//        newStudent.setBirthDate(Date.valueOf("1999-05-07"));
//        newStudent.setEmail("ivanova@gmai.com");
//        newStudent.setSerialNumber("4040 546908");
//        newStudent.setPassword("Juli1234");
//        studentRepository.save(newStudent);
    }

    @Test
    public void testCollege(){
        List<College> colleges = collegeRepository.
                findAllByNameLikeIgnoreCaseOrAbbreviationLikeIgnoreCase("mosc%", "it%");
        System.out.println(colleges);


//        College itmo = collegeRepository.findById(1).get();
//        System.out.println(itmo.getSpecialities());
//        System.out.println(itmo.getName());
//
//        College msu = new College();
//        msu.setName("Moscow State University");
//        msu.setAbbreviation("MSU");
//        msu.setCity("Moscow");
//        msu.setDescription("lo lo la lo");
//        collegeRepository.save(msu);
    }

    @Test
    public void testSpeciality(){
        List<Speciality> specialities = specialityRepository.findByNameLikeIgnoreCase("v%");
        specialities.forEach(s -> log.info(s.getName()));
    }

    @Test
    public void testExams(){
        Student student = studentRepository.findWithExamsById(3);
        Subject math = subjectRepository.findByNameIgnoreCase("math");
        student.addExam(math, 94);
        studentRepository.save(student);
        log.info("time");
    }


    @Test
    public void testOlympiad(){
        Olympiad vosh = olympiadRepository.findByNameIgnoreCaseAndSubjectId("vosh", 1);
        Student student = studentRepository.findWithOlympiadsById(2);

        student.getOlympiads().remove(vosh);
        studentRepository.save(student);

    }

    @Test
    public void testRating(){
        Speciality vt = specialityRepository.findById(1).get();
        Student student = studentRepository.findWithRatingsById(1);
        student.cancelApplication(vt);
        studentRepository.save(student);
    }

    @Test
    public void testAch(){
        Achievement essay = achievementRepository.findByNameIgnoreCase("essay");
        Student student = studentRepository.findWithScoresById(2);
        student.getAchievements().removeIf(a -> a.equals(essay));
        studentRepository.save(student);
    }

    @Test
    public void testReqs(){
        Speciality vt = specialityRepository.findWithReqsById(1);
        System.out.println(vt.getRequirements());
    }

    @Test
    public void testCollegeScores(){
        College itmo = collegeRepository.findWithScoresById(1);
        System.out.println(itmo.getAchievementsScore());
    }

    @Test
    public void testMessages(){
        Student student = studentRepository.findById(1).get();
        Worker worker = workerRepository.findById(1).get();

        Message message = new Message();
        message.setStudent(student);
        message.setWorker(worker);
        message.setDate(new Date());
        message.setFromStudent(true);
        message.setMessage("test test test");
        messageRepository.save(message);
    }

    @Test
    public void testSignCheck(){
        Student student = studentRepository.findBySerialNumber("0123 456789");
        System.out.println(student);
    }


}

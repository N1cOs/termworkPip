import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.ifmo.se.termwork.config.JpaConfig;
import ru.ifmo.se.termwork.domain.*;
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
        Student student = studentRepository.findStudentWithScoresById(2);
        System.out.println(student.getExams());
//        Subject math = subjectRepository.findByNameIgnoreCase("math");
//        Student student = studentRepository.findStudentWithScoresById(3);
//        Exam exam = new Exam(new ExamId(student, math), 60);
//        student.getExams().add(exam);
//        System.out.println("...saving");
//        studentRepository.save(student);
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

    @Test
    public void testCollegeScores(){
        College itmo = collegeRepository.findWithScoresById(1);
        System.out.println(itmo.getAchievementsScore());
    }

    @Test
    public void testMessages(){
//        Student student = studentRepository.findWithMessagesById(1);
//        Worker worker = workerRepository.findWithMessagesById(1);
        messageRepository.findAllWithAttributesByStudentIdAndWorkerIdOrderByDate(1, 1).
                forEach(n -> System.out.println(n.getStudent().getName()));
//        Worker worker = workerRepository.findById(1).orElse(null);
//        Student student = studentRepository.findById(1).orElse(null);
//        MessageId id = new MessageId(worker, student);
//
//        Message message1 = new Message();
//        message1.setId(id);
//        message1.setDate(new Timestamp(100000000000l));
//        message1.setMessage("hello this test");
//
//        Message message2 = new Message();
//        message2.setId(id);
//        message2.setDate(new Timestamp(1000000000000000000l));
//        message2.setMessage("am, what is going on?");
//
//        System.out.println(message1.hashCode() == message2.hashCode());


    }


}
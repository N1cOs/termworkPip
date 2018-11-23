import org.junit.Assert;
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
import java.util.List;
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
        Student student = studentRepository.
                findByEmailIgnoreCaseOrPhone("ivanova@gmaiL.com", "+79112938535");
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
        Speciality speciality = new Speciality();
        speciality.setCollege(collegeRepository.getOne(1));
        speciality.setName("KT");
        speciality.setOkso("09.03.00");
        specialityRepository.save(speciality);

        System.out.println(specialityRepository.findById(1).get());

    }

    @Test
    public void testExams(){
        Student student = studentRepository.findWithScoresById(2);
        Subject math = subjectRepository.findByNameIgnoreCase("math");
        student.deleteExam(math);
        studentRepository.save(student);
//        student.getExams().stream().filter(n -> n.getId().getSubject().
//                equals(subjects.get(0))).forEach(n -> n.setScore(89));
//        studentRepository.save(student);
//        studentRepository.save(student);
//        Exam exam = new Exam(new ExamId(student, russian), 52);
//        student.getExams().add(exam);
//        studentRepository.save(student);
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
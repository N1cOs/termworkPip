import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.ifmo.se.termwork.config.JpaConfig;
import ru.ifmo.se.termwork.domain.*;
import ru.ifmo.se.termwork.repository.*;
import ru.ifmo.se.termwork.security.Role;

import java.text.ParseException;

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

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    public void testStudent() throws ParseException {
//        Student student = new Student();
//        student.setName("Nick");
//        student.setSurname("Karmatskikh");
//        student.setEmail("admin@admin.com");
//        student.setPassword(passwordEncoder.encode("1234"));
//        student.setEnabled(true);
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        student.setBirthDate(dateFormat.parse("2000-02-26"));
//        student.setSerialNumber("0123 456789");
//        studentRepository.save(student);
        Student student = studentRepository.findBySerialNumber("0123 456789").orElseThrow(IllegalArgumentException::new);
        student.setRoles(Role.STUDENT);
        studentRepository.save(student);
    }

    @Test
    public void testCollege(){
        College college = new College();
        college.setName("ITMO");
        college.setCity("St-Petersburg");
        college.setAbbreviation("ITMO");
        college.setDescription("la la la");
        collegeRepository.save(college);
    }

    @Test
    public void testWorker(){
        College college = collegeRepository.findById(1).orElseThrow(IllegalArgumentException::new);
        Worker worker = new Worker();
        worker.setName("lal");
        worker.setSurname("worker");
        worker.setEmail("worker@worker.com");
        worker.setPassword(passwordEncoder.encode("1234"));
        worker.setEnabled(true);
        worker.setCollege(college);
        workerRepository.save(worker);
    }


}
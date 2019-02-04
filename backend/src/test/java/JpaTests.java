import lombok.extern.log4j.Log4j;
import org.apache.log4j.pattern.LineSeparatorPatternConverter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.ifmo.se.termwork.config.JpaConfig;
import ru.ifmo.se.termwork.domain.*;
import ru.ifmo.se.termwork.dto.OlympiadDto;
import ru.ifmo.se.termwork.repository.CollegeRepository;
import ru.ifmo.se.termwork.repository.StudentRepository;
import ru.ifmo.se.termwork.repository.WorkerRepository;
import ru.ifmo.se.termwork.dto.StudentDto;
import ru.ifmo.se.termwork.security.Role;
import ru.ifmo.se.termwork.service.mappers.StudentMapper;
import ru.ifmo.se.termwork.service.mappers.StudentMapperImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    public void testStudent() throws ParseException {
        Student student = new Student();
        student.setName("Nick");
        student.setSurname("Karmatskikh");
        student.setEmail("admin@admin.com");
        student.setPassword(passwordEncoder.encode("5678"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        student.setBirthDate(dateFormat.parse("2000-02-26"));
        student.setSerialNumber("0123 456789");
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


    @Autowired
    private StudentMapper studentMapper;

    @Test
    public void testMapper() {
        StudentDto studentDto = new StudentDto();
        studentDto.setName("name");
        studentDto.setSurname("surname");
        studentDto.setBirthDate(new Date());
        studentDto.setEmail("em@em.ru");
        studentDto.setPhone("+7phonenumber");
        studentDto.setPassword("abracadabra");
        studentDto.setSerialNumber("3333 333333");
        OlympiadDto olympiadDto = new OlympiadDto();
//        ввожу заведомо неверные данные для олимпиады
        olympiadDto.setLevel(1);
        olympiadDto.setName("математика");
        olympiadDto.setSerialNumber("221112");
        olympiadDto.setSubjectName("Всероссийская олимпиада математиков");
        List<OlympiadDto> olympiadDtos = new ArrayList<OlympiadDto>();
        olympiadDtos.add(olympiadDto);
        studentDto.setOlympiads(olympiadDtos);
        Student student = studentMapper.studentDtoToStudent(studentDto);
        System.out.println(student);


    }
}

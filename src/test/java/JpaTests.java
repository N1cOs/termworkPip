import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.ifmo.se.termwork.config.JpaConfig;
import ru.ifmo.se.termwork.domain.Student;
import ru.ifmo.se.termwork.repository.StudentRepository;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {JpaConfig.class})
public class JpaTests {
    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void testStudent(){

        Student student = studentRepository.findById(1).get();
        System.out.println(student.getName());

    }
}

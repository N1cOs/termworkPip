import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.ifmo.se.termwork.config.MailConfig;

@Log4j
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {MailConfig.class})
public class MailTest {

    @Autowired
    private JavaMailSender javaMailSender;

    @Test
    public void testMail(){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo("chistohodovda@gmail.com");
        simpleMailMessage.setSubject("test");
        simpleMailMessage.setText("привет, здарова!");
        javaMailSender.send(simpleMailMessage);
    }
}

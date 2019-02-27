import org.jivesoftware.smackx.iqregister.AccountManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.jxmpp.jid.parts.Localpart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.ifmo.se.termwork.config.RootConfig;
import ru.ifmo.se.termwork.service.JabberService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {RootConfig.class})
public class XMPPTest {

    @Autowired
    private AccountManager accountManager;

    @Autowired
    private JabberService jabberService;

    @Test
    public void testSignUp() throws Exception {
        Localpart login = Localpart.fromOrNull("test2");
        accountManager.createAccount(login, "test");
    }

    @Test
    public void testSendingMessages(){
        jabberService.sendMessage("admin", "hi from bot!");
    }
}

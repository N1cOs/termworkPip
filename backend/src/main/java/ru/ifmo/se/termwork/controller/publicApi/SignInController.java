package ru.ifmo.se.termwork.controller.publicApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.se.termwork.domain.Student;
import ru.ifmo.se.termwork.domain.Worker;
import ru.ifmo.se.termwork.dto.UserDTO;
import ru.ifmo.se.termwork.repository.StudentRepository;
import ru.ifmo.se.termwork.repository.WorkerRepository;
import ru.ifmo.se.termwork.security.Authorities;
import ru.ifmo.se.termwork.security.JwtUtils;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@RequestMapping("/public")
public class SignInController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(path = "/signin",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity signIn(@RequestBody @Valid UserDTO user) {
        String email = user.getUsername();
        Student student = studentRepository.findByEmail(email);
        Worker worker = workerRepository.findByEmail(email);

        int id;
        String hashPassword;
        ArrayList<String> roles = new ArrayList<>();
        if(worker != null) {
            hashPassword = worker.getPassword();
            roles.add(Authorities.WORKER);
            id = worker.getId();
            if(worker.isHeadWorker())
                roles.add(Authorities.HEAD_WORKER);
        }
        else if(student != null) {
            hashPassword = student.getPassword();
            roles.add(Authorities.STUDENT);
            id = student.getId();
        }
        else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        if(!passwordEncoder.matches(user.getPassword(), hashPassword))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        return ResponseEntity.ok(jwtUtils.getToken(id, roles));
    }
}

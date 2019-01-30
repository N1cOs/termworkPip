package ru.ifmo.se.termwork.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ifmo.se.termwork.domain.Student;
import ru.ifmo.se.termwork.domain.Worker;
import ru.ifmo.se.termwork.dto.CredentialDTO;
import ru.ifmo.se.termwork.repository.StudentRepository;
import ru.ifmo.se.termwork.repository.WorkerRepository;
import ru.ifmo.se.termwork.security.Authorities;
import ru.ifmo.se.termwork.service.ChangeCredentialsService;

@Service
public class SimpleChangeCredentialsService implements ChangeCredentialsService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void changeEmail(int userId, CredentialDTO credential, String authority) {
        if(authority.equals(Authorities.STUDENT)){
            Student student = studentRepository.findById(userId).
                    orElseThrow(() -> new AccessDeniedException("User doesn't exist"));
            if(passwordEncoder.matches(credential.getPassword(), student.getPassword())){
                student.setEmail((credential.getNewValue()));
                studentRepository.save(student);
            }
            else
                throw new AccessDeniedException("Password is incorrect");
        }
        else{
            Worker worker = workerRepository.findById(userId).
                    orElseThrow(() -> new AccessDeniedException("User doesn't exist"));
            if(passwordEncoder.matches(credential.getPassword(), worker.getPassword())){
                worker.setEmail(credential.getNewValue());
                workerRepository.save(worker);
            }
            else
                throw new AccessDeniedException("Password is incorrect");
        }
    }

    @Override
    public void changePassword(int userId, CredentialDTO credential, String authority) {
        if(authority.equals(Authorities.STUDENT)){
            Student student = studentRepository.findById(userId).
                    orElseThrow(() -> new AccessDeniedException("User doesn't exist"));
            if(passwordEncoder.matches(credential.getPassword(), student.getPassword())){
                student.setPassword(passwordEncoder.encode(credential.getNewValue()));
                studentRepository.save(student);
            }
            else
                throw new AccessDeniedException("Password is incorrect");
        }
        else{
            Worker worker = workerRepository.findById(userId).
                    orElseThrow(() -> new AccessDeniedException("User doesn't exist"));
            if(passwordEncoder.matches(credential.getPassword(), worker.getPassword())){
                worker.setPassword(passwordEncoder.encode(credential.getNewValue()));
                workerRepository.save(worker);
            }
            else
                throw new AccessDeniedException("Password is incorrect");
        }
    }
}

package ru.ifmo.se.termwork.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ifmo.se.termwork.domain.User;
import ru.ifmo.se.termwork.dto.CredentialDTO;
import ru.ifmo.se.termwork.repository.UserRepository;
import ru.ifmo.se.termwork.service.ChangeCredentialsService;

@Service
public class SimpleChangeCredentialsService implements ChangeCredentialsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void changeEmail(int userId, CredentialDTO credential) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AccessDeniedException("User not found"));
        if(passwordEncoder.matches(credential.getPassword(), user.getPassword())){
            user.setEmail(credential.getNewValue());
            userRepository.save(user);
        }
        else
            throw new AccessDeniedException("Password is incorrect");
    }

    @Override
    public void changePassword(int userId, CredentialDTO credential) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AccessDeniedException("User not found"));
        if(passwordEncoder.matches(credential.getPassword(), user.getPassword())){
            user.setPassword(passwordEncoder.encode(credential.getNewValue()));
            userRepository.save(user);
        }
        else
            throw new AccessDeniedException("Password is incorrect");
    }
}

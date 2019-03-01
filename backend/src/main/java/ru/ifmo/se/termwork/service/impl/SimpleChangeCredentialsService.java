package ru.ifmo.se.termwork.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ifmo.se.termwork.domain.User;
import ru.ifmo.se.termwork.dto.CredentialDto;
import ru.ifmo.se.termwork.repository.UserRepository;
import ru.ifmo.se.termwork.service.ChangeCredentialsService;
import ru.ifmo.se.termwork.support.exception.ApiException;
import ru.ifmo.se.termwork.support.exception.ClientException;
import ru.ifmo.se.termwork.support.exception.InputErrors;

@Service
public class SimpleChangeCredentialsService implements ChangeCredentialsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void changeEmail(int userId, CredentialDto credential) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "exception.user.notFound"));
        if(passwordEncoder.matches(credential.getPassword(), user.getPassword())){
            user.setEmail(credential.getNewValue());
            userRepository.save(user);
        }
        else
            throw new ClientException(HttpStatus.FORBIDDEN, InputErrors.Invalid.PASSWORD);
    }

    @Override
    public void changePassword(int userId, CredentialDto credential) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "exception.user.notFound"));
        if(passwordEncoder.matches(credential.getPassword(), user.getPassword())){
            user.setPassword(passwordEncoder.encode(credential.getNewValue()));
            userRepository.save(user);
        }
        else
            throw new ClientException(HttpStatus.FORBIDDEN, InputErrors.Invalid.PASSWORD);
    }
}

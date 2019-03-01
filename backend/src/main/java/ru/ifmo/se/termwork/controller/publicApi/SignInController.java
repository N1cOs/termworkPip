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
import ru.ifmo.se.termwork.domain.Authority;
import ru.ifmo.se.termwork.domain.User;
import ru.ifmo.se.termwork.dto.UserDto;
import ru.ifmo.se.termwork.repository.UserRepository;
import ru.ifmo.se.termwork.security.JwtUtils;
import ru.ifmo.se.termwork.support.exception.ClientException;
import ru.ifmo.se.termwork.support.exception.InputErrors;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/public")
public class SignInController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(path = "/sign-in",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity signIn(@RequestBody @Valid UserDto userDTO) {
        User user = userRepository.findByEmail(userDTO.getUsername()).
                orElseThrow(() -> new ClientException(HttpStatus.UNAUTHORIZED, InputErrors.Invalid.EMAIL));

        if(!passwordEncoder.matches(userDTO.getPassword(), user.getPassword()))
            throw new ClientException(HttpStatus.UNAUTHORIZED, InputErrors.Invalid.PASSWORD);

        List<String> roles = user.getAuthorities().stream().map(Authority::getName).collect(Collectors.toList());
        return ResponseEntity.ok(jwtUtils.getToken(user.getId(), roles));
    }
}

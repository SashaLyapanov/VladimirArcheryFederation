package com.example.kursachrps.controllers;

import com.example.kursachrps.models.Role;
import com.example.kursachrps.models.Sportsman;
import com.example.kursachrps.models.Status;
import com.example.kursachrps.dto.AuthAndRegistration.LoginDTO;
import com.example.kursachrps.dto.AuthAndRegistration.SignUpDTO;
import com.example.kursachrps.dto.UserDTO;
import com.example.kursachrps.mapper.SportsmanMapper;
import com.example.kursachrps.mapper.UserMapper;
import com.example.kursachrps.models.User;
import com.example.kursachrps.repositories.SportsmanRepository;
import com.example.kursachrps.repositories.UserMainRepository;
import com.example.kursachrps.service.AuthenticationService;
import com.example.kursachrps.service.SmtpMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserMainRepository userMainRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SportsmanMapper sportsmanMapper;

    @Autowired
    private SportsmanRepository sportsmanRepository;

    @Autowired
    private SmtpMailSender smtpMailSender;


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userMainRepository.findByEmail(loginDTO.getEmail()).orElse(null);
        if (user != null && Objects.equals(user.getActivationCode(), "true")) {
            UserDTO userDTO = userMapper.transform(user);

            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Please, check your email and activate account.", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<String> registrationUser(@RequestBody SignUpDTO signUpDTO) {
        // Проверка на условие, что такого пользователя еще нет в БД
        if (userMainRepository.existsByEmail(signUpDTO.getEmail())) {
            return new ResponseEntity<>("This email address is already registered in the system.", HttpStatus.BAD_REQUEST);
        }
        // Создаем спортсмена
        Sportsman sportsman = sportsmanMapper.fromSignUpDTO(signUpDTO);
        sportsman.setActivationCode(UUID.randomUUID().toString());
        sportsman.setRole(Role.SPORTSMAN);
        sportsman.setStatus(Status.ACTIVE);
        sportsman.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));

        sportsmanRepository.save(sportsman);

        //TODO
        // изменить ссылку для фронта (клиентской части), чтобы в письме было указано http://localhost:3000/activate/$s
        // и на фронте уже выполнять запрос к беку на контроллер activate/{code} (который представлен ниже)
        if (!StringUtils.isEmpty(sportsman.getEmail())) {
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to our Archery Federation. Please, visit next link: http://localhost:8080/api/v1/auth/activate/%s",
                    sportsman.getFirstName(),
                    sportsman.getActivationCode()
            );

//            smtpMailSender.send(sportsman.getEmail(), "Activation code", message);

        }

        return new ResponseEntity<>("Your account has been created, please check you email.", HttpStatus.OK);
    }

    @GetMapping("activate/{code}")
    public ResponseEntity<?> activate(@PathVariable String code) {
        boolean isActivated = authenticationService.activateUser(code);

        if (isActivated) {
            return new ResponseEntity<>("User successfully activated", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Activation code isn't found", HttpStatus.BAD_REQUEST);
        }
    }

}


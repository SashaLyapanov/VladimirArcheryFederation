package com.example.kursachrps.controllers;

import com.example.kursachrps.models.Role;
import com.example.kursachrps.models.Sportsman;
import com.example.kursachrps.models.Status;
import com.example.kursachrps.dto.AuthAndRegistration.LoginDTO;
import com.example.kursachrps.dto.AuthAndRegistration.SignUpDTO;
import com.example.kursachrps.dto.UserDTO;
import com.example.kursachrps.mapper.SportsmanMapper;
import com.example.kursachrps.mapper.UserMapper;
import com.example.kursachrps.repositories.SportsmanMainRepository;
import com.example.kursachrps.repositories.UserMainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserMainRepository userMainRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SportsmanMapper sportsmanMapper;

    @Autowired
    private SportsmanMainRepository sportsmanMainRepository;



    @PostMapping("/signin")
    public UserDTO authenticateUser(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDTO userDTO = userMapper.transform(userMainRepository.findByEmail(loginDTO.getEmail()).orElse(null));
//        User user = userMainRepository.findByEmail(loginDTO.getEmail()).orElse(null);
        return userDTO;
        //////
        //вот в этом методе короче надо на фронт передавать json, в котором хранится весь пользователь (UserDTO), откуда мы узнаем и роль и т.д.
    }


    @PostMapping("/signup")
    public ResponseEntity<String> registrationUser(@RequestBody SignUpDTO signUpDTO) {

        // Проверка на условие, что такого пользователя еще нет в БД
        if (userMainRepository.existsByEmail(signUpDTO.getEmail())) {
            return new ResponseEntity<>("This email address is already registered in the system.", HttpStatus.BAD_REQUEST);
        }

        // Создаем спортсмена
        Sportsman sportsman = new Sportsman();
        sportsman = sportsmanMapper.fromSignUpDTO(signUpDTO);
        sportsman.setRole(Role.SPORTSMAN);
        sportsman.setStatus(Status.ACTIVE);
        sportsman.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));

        sportsmanMainRepository.save(sportsman);

        return new ResponseEntity<>("Sportsman registered successfully", HttpStatus.OK);

    }
}


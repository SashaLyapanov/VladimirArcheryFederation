package com.example.kursachrps.controllers;

import com.example.kursachrps.Models.User;
import com.example.kursachrps.dto.AuthAndRegistration.LoginDTO;
import com.example.kursachrps.mapper.UserMapper;
import com.example.kursachrps.repositories.UserMainRepository;
import org.springframework.beans.factory.annotation.Autowired;
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


//    @PostMapping("/signin")
//    public ResponseEntity<String> authenticateUser(@RequestBody LoginDTO loginDTO) {
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        return new ResponseEntity<>("User signed-in successfully!", HttpStatus.OK);
//        //////
//        //вот в этом методе короче надо на фронт передавать json, в котором хранится весь пользователь (UserDTO), откуда мы узнаем и роль и т.д.
//    }

    @PostMapping("/signin")
    public User authenticateUser(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

//        UserDTO userDTO = userMapper.transform(userMainRepository.findByEmail(loginDTO.getEmail()).orElse(null));
        User user = userMainRepository.findByEmail(loginDTO.getEmail()).orElse(null);
        return user;
        //////
        //вот в этом методе короче надо на фронт передавать json, в котором хранится весь пользователь (UserDTO), откуда мы узнаем и роль и т.д.
    }


//    @PostMapping("/signup")
//    public ResponseEntity<String> registrationUser(@RequestBody RegistrationDTO registrationDTO) {
//
//    }

}


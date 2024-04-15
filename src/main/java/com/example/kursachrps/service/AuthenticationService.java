package com.example.kursachrps.service;

import com.example.kursachrps.models.User;
import com.example.kursachrps.repositories.UserMainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private UserMainRepository userMainRepository;

    public boolean activateUser(String code) {
        User user = userMainRepository.findByActivationCode(code).orElse(null);

        if (user == null) {
            return false;
        }

        user.setActivationCode("true");
        userMainRepository.save(user);

        return true;
    }
}

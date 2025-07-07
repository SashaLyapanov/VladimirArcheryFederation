package com.example.kursachrps.security;

import com.example.kursachrps.models.User;
import com.example.kursachrps.repositories.RegistrAndAuth.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден: " + email));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), user.getRole().getAuthority());
    }

    public UserDetails loadUserByUserId(String id) throws UsernameNotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден: " + id));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), user.getRole().getAuthority());
    }
}

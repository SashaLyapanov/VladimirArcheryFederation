package com.example.kursachrps.services.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserDetailsServiceImpl {
    UserDetails loadUserByUserName(String username) throws UsernameNotFoundException;
}

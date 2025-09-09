package com.example.kursachrps.exceptions;

public class JwtTokenExpiredException extends JwtAuthenticationException {
    public JwtTokenExpiredException(String message) {
        super(message);
    }
}

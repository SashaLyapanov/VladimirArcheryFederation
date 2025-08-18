package com.example.kursachrps.config;

import org.springframework.http.ResponseCookie;

import java.time.Duration;

public class CookieConfig {

    public static ResponseCookie accessCookie(String jwt, boolean https) {
        return ResponseCookie.from("accessToken", jwt)
                .httpOnly(true)
                .secure(https)
                .path("/")
                .sameSite("Strict")
                .maxAge(Duration.ofMinutes(10))
                .build();
    }

    public static ResponseCookie refreshCookie(String jwt, boolean https) {
        return ResponseCookie.from("refreshToken", jwt)
                .httpOnly(true)
                .secure(https)
                .path("/auth")
                .sameSite("Strict")
                .maxAge(Duration.ofDays(30))
                .build();
    }

    public static ResponseCookie clearCookie(String name, boolean https) {
        return ResponseCookie.from(name, "")
                .httpOnly(true)
                .secure(https)
                .path("/")
                .sameSite("Strict")
                .maxAge(0)
                .build();
    }

}

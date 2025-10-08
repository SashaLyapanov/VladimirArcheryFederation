package com.example.kursachrps.controllers;

import com.example.kursachrps.models.*;
import com.example.kursachrps.dto.AuthAndRegistration.LoginDTO;
import com.example.kursachrps.dto.AuthAndRegistration.SignUpDTO;
import com.example.kursachrps.dto.UserDTO;
import com.example.kursachrps.mapper.SportsmanMapper;
import com.example.kursachrps.mapper.UserMapper;
import com.example.kursachrps.repositories.SportsmanRepository;
import com.example.kursachrps.repositories.UserMainRepository;
import com.example.kursachrps.security.JwtUtils;
import com.example.kursachrps.security.RefreshTokenService;
import com.example.kursachrps.service.AuthenticationService;
import com.example.kursachrps.service.SmtpMailSender;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
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

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RefreshTokenService refreshTokenService;

//    @Autowired
//    private CookieU

    private final String sameSite = "Strict";

    private final String cookieDomain = null;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginDTO loginDTO, HttpServletRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));

        User user = userMainRepository.findByEmail(loginDTO.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (user != null && Objects.equals(user.getActivationCode(), "true")) {
            UserDTO userDTO = userMapper.transform(user);
            String accessToken = jwtUtils.generateAccessToken(user);
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

//            boolean https = request.isSecure();
//            var accessCookie = CookieConfig.accessCookie(accessToken, https);
//            var refreshCookie = CookieConfig.refreshCookie(refreshToken.getToken(), https);
//            return ResponseEntity.ok()
//                    .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
//                    .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
//                    .body(Map.of(
//                            "userData", userDTO
//                    ));

            return ResponseEntity.ok(Map.of(
                    "accessToken", accessToken,
                    "refreshToken", refreshToken.getToken(),
                    "userData", userDTO
            ));
        } else {
            return new ResponseEntity<>("Please, check your email and activate account.", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");

        String userId = jwtUtils.extractUserIdForRefreshToken(refreshToken);

        return refreshTokenService.getRefreshTokenByUserId(userId)
                .filter(token -> token.getToken().equals(refreshToken))
                .map(token -> {
                    if (token.getExpiryDate().isBefore(LocalDateTime.now())) {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token expired");
                    }
                    String newAccessToken = jwtUtils.generateAccessToken(token.getUser());
                    return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
                })
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token"));
    }


    @PostMapping("/signup")
    public ResponseEntity<String> registrationUser(@RequestBody SignUpDTO signUpDTO) {
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

        if (!org.apache.commons.lang3.StringUtils.isEmpty(signUpDTO.getEmail())) {
            String message = String.format(
                    "Здравствуйте, %s! \n" +
                            "Мы очень рады, что вы заинтересовались нашим продуктом и успешно прошли регистрацию! \n" +
                            "Пожалуйста, перейдите по ссылке для активации вашего профиля: https://api.fslvo.ru/api/v1/auth/activate/%s/%s",
                    sportsman.getFirstName() + " " + sportsman.getSurname(),
                    sportsman.getEmail(),
                    sportsman.getActivationCode()
            );

            smtpMailSender.send(sportsman.getEmail(), "Activation code", message);
        }

        return new ResponseEntity<>("Your account has been created, please check you email.", HttpStatus.OK);
    }

    @GetMapping("activate/{email}/{code}")
    public ResponseEntity<?> activate(@PathVariable String email, @PathVariable String code) {
        boolean isActivated = authenticationService.authenticate(email, code);

        if (isActivated) {
            return new ResponseEntity<>("Success! Welcome to our system: <a href='https://fslvo.ru/login'>Login page!</a>", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The bad news is that the account could not be activated. Contact customer support.", HttpStatus.BAD_REQUEST);
        }
    }

//    @PostMapping("/logout")
//    public ResponseEntity<Void> logout(HttpServletRequest request) {
//        boolean https = request.isSecure();
//
//        var clearAccess = CookieConfig.clearCookie("accessToken", https);
//        var clearRefresh = CookieConfig.clearCookie("refreshToken", https);
//
//        return ResponseEntity.noContent()
//                .header(HttpHeaders.SET_COOKIE, clearAccess.toString())
//                .header(HttpHeaders.SET_COOKIE, clearRefresh.toString())
//                .build();
//    }

}


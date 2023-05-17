package com.example.kursachrps.config;

import com.example.kursachrps.Models.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive.COOKIES;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                    .invalidSessionUrl("/api/auth/signin"))
                .cors(withDefaults())
                .csrf().disable()
//                .authorizeHttpRequests(auth -> {
                .authorizeHttpRequests((auth) ->
                    auth.requestMatchers("/**").permitAll()
                            .requestMatchers("/api/auth/**").permitAll()
                            .anyRequest().authenticated()
                )

//                    auth.requestMatchers("/api/auth/signin").permitAll();
//                    auth.requestMatchers("/index.html").permitAll();
//                    auth.requestMatchers("/api/v1/admin/sportsmen").permitAll();
//                    auth.requestMatchers("/api/v1/admin/coaches").permitAll();
//                    auth.requestMatchers("/api/v1/admin/coach**").permitAll();
//                    auth.requestMatchers("/api/v1/admin/sportsman**").permitAll();
//                    auth.requestMatchers("/api/v1/admin/editSportsman**").permitAll();
//                    auth.requestMatchers("/api/v1/admin/competitions").permitAll();
//                    auth.requestMatchers("/api/v1/admin/blockUser**").permitAll();
//                    auth.requestMatchers(HttpMethod.POST, "/api/v1/admin/**").hasAuthority(Permission.SPORTSMAN_WRITE.getPermission());
//                    auth.requestMatchers(HttpMethod.PUT, "/api/v1/admin/**").hasAuthority(Permission.SPORTSMAN_WRITE.getPermission());
//                    auth.requestMatchers(HttpMethod.DELETE, "/api/v1/admin/**").hasAuthority(Permission.SPORTSMAN_DELETE.getPermission());
//                    auth.requestMatchers(HttpMethod.PATCH, "/api/v1/admin/**").hasAuthority(Permission.SPORTSMAN_UPDATE.getPermission());
//                    auth.requestMatchers(HttpMethod.GET, "/api/**").hasAuthority(Permission.SPORTSMEN_READ.getPermission());
//                })
                .logout((logout) -> logout
                .addLogoutHandler(new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(COOKIES))))

//                .build();
//
                .httpBasic().and().build();
    }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        configuration.setAllowedOrigins(List.of("http://localhost:3000/"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PUT","OPTIONS","PATCH", "DELETE"));
        configuration.setAllowCredentials(true);
        configuration.setExposedHeaders(List.of("Authorization"));
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    protected AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


//    @Bean
//    public HttpSessionEventPublisher httpSessionEventPublisher() {
//        return new HttpSessionEventPublisher();
//    }

}

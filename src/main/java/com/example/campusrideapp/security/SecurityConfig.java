package com.example.campusrideapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                //.csrf(csrf -> csrf.disable()) // band-aid for now, On Thymeleaf add the admin, mngr, assoc authorization
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/", "/error", "/style.css", "/images/**").permitAll()
                        .requestMatchers("/list").permitAll()
                        .requestMatchers("/login",
                                "/login/**",
                                "/view/current/{id}/edit",
                                "/view/current/{id}/delete",
                                "/view/current/{id}",
                                "/register",
                                "/register/**",
                                "/user/register")
                            .hasAnyRole( "ADMIN", "DRIVER", "PASSENGER", "BOTH")
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/list", true)
                        .permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl("/list")
                        .permitAll())
                .build();
    }
}

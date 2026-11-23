package com.example.campusrideapp;

import com.example.campusrideapp.domains.User;
import com.example.campusrideapp.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class CampusRideAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(CampusRideAppApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader(UserService userService, PasswordEncoder passwordEncoder){
        return args -> {
            userService.createUserIfMissing(new User("uTest", passwordEncoder.encode("user123"), "USER TEST", "ROLE_ADMIN"));
            userService.createUserIfMissing(new User("admin", passwordEncoder.encode("admin"), "USER TEST", "ROLE_ADMIN"));
            userService.createUserIfMissing(new User("admin1", passwordEncoder.encode("admin1"), "USER TEST", "ROLE_ADMIN"));
            userService.createUserIfMissing(new User("driver", passwordEncoder.encode("driver123"), "DRIVER TEST", "ROLE_DRIVER"));
            userService.createUserIfMissing(new User("passenger", passwordEncoder.encode("passenger"), "PASSENGER", "ROLE_PASSENGER"));
            userService.createUserIfMissing(new User("mTest", passwordEncoder.encode("mngr123"), "MNGR TEST", "ROLE_PASSENGER"));
            userService.createUserIfMissing(new User("aTest", passwordEncoder.encode("assoc123"), "ASSOC TEST", "ROLE_ASSOC"));
            userService.createUserIfMissing(new User("uUser", passwordEncoder.encode("test123"), "USER USER", "ROLE_USER"));
        };
    }
}

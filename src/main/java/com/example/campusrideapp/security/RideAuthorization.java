package com.example.campusrideapp.security;

import com.example.campusrideapp.data.RideRepository;
import com.example.campusrideapp.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class RideAuthorization {
    private final RideRepository rideRepo;

    public RideAuthorization(RideRepository rr) { rideRepo = rr; }
}

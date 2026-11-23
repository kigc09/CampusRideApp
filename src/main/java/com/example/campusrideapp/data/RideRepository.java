package com.example.campusrideapp.data;

import com.example.campusrideapp.domains.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface RideRepository extends JpaRepository<Ride, UUID> {
    @Modifying
    @Query("""
            UPDATE Ride r
            SET r.seatsAvailable = r.seatsAvailable -1
            WHERE r.id = :id
            AND r.seatsAvailable > 0
            """)
    int decreaseAvailableSeats(@Param("id") UUID id);
}
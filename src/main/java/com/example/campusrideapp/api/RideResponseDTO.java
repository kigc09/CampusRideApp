package com.example.campusrideapp.api;

import com.example.campusrideapp.domains.Campus;
import com.example.campusrideapp.domains.Ride;
import com.example.campusrideapp.domains.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

public class RideResponseDTO {
    private UUID id;
    private String name;
    private String pickupLocation;
    private LocalDateTime pickupTime;
    private Campus campus;
    private int seatsAvailable;
    private Status status;

    public RideResponseDTO(){}


    public static RideResponseDTO from (Ride ride){
        RideResponseDTO responseDTO = new RideResponseDTO();

        responseDTO.setId(ride.getId());
        responseDTO.setName(ride.getName());
        responseDTO.setPickupLocation(ride.getPickupLocation());
        responseDTO.setPickupTime(ride.getPickupTime());
        responseDTO.setCampus(ride.getCampus());
        responseDTO.setSeatsAvailable(ride.getSeatsAvailable());
        responseDTO.setStatus(ride.getStatus());

        return responseDTO;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public LocalDateTime getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(LocalDateTime pickupTime) {
        this.pickupTime = pickupTime;
    }

    public Campus getCampus() {
        return campus;
    }

    public void setCampus(Campus campus) {
        this.campus = campus;
    }

    public int getSeatsAvailable() {
        return seatsAvailable;
    }

    public void setSeatsAvailable(int seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

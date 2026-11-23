package com.example.campusrideapp.api;

import com.example.campusrideapp.domains.Campus;
import com.example.campusrideapp.domains.Status;

import java.time.LocalDateTime;
import java.util.UUID;

public class RidePatchRequestDTO {
    private UUID id;
    private String name;
    private String pickupLocation;
    private LocalDateTime pickupTime;
    private Campus campus;
    private int seatsAvailable;
    private Status status;

    public RidePatchRequestDTO() {}

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

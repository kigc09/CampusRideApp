package com.example.campusrideapp.domains;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String pickupLocation;
    @NotNull
    @Column(name = "pickup_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime pickupTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Campus campus;
    @Column(nullable = false)
    private int seatsAvailable;
    @Column(nullable = false)
    private Status status;

    public Ride(){}

    public Ride(UUID id, String name, String pickupLocation, LocalDateTime pickupTime, Campus campus, int seatsAvailable, Status status){
        this.id = id;
        this.name = name;
        this.pickupLocation = pickupLocation;
        this.pickupTime = pickupTime;
        this.campus = campus;
        this.seatsAvailable = seatsAvailable;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    @Override
    public String toString(){
        return "RIDE {" +
                "id=" + id +
                " name='" + name + "\'" +
                " pickupLocation= " + pickupLocation +
                " pickupTime= " + pickupTime +
                " campus= " + campus +
                " seatsAvailable= " + seatsAvailable +
                " status= " + status +
                "}";
    }
}


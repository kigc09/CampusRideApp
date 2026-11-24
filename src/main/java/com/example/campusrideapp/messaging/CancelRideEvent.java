package com.example.campusrideapp.messaging;

import com.example.campusrideapp.domains.Campus;
import com.example.campusrideapp.domains.Ride;
import com.example.campusrideapp.domains.Status;

import java.util.UUID;

public class CancelRideEvent {
    private String eventId;
    private UUID rideId;
    private String name;
    private Campus campus;
    private Status status;

    public CancelRideEvent(){}

    public static CancelRideEvent fromRide(Ride ride){
        CancelRideEvent event = new CancelRideEvent();

        event.setEventId(UUID.randomUUID().toString());
        event.setRideId(ride.getId());
        event.setName(ride.getName());
        event.setCampus(ride.getCampus());
        event.setStatus(ride.getStatus());

        return event;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public UUID getRideId() {
        return rideId;
    }

    public void setRideId(UUID rideId) {
        this.rideId = rideId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Campus getCampus() {
        return campus;
    }

    public void setCampus(Campus campus) {
        this.campus = campus;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

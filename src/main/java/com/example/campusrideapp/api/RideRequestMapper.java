package com.example.campusrideapp.api;

import com.example.campusrideapp.domains.Ride;

public class RideRequestMapper {
    private RideRequestMapper(){}

    public static Ride fromCreate(RideCreateRequestDTO requestDTO){
        Ride ride = new Ride();

        ride.setId(ride.getId());
        ride.setName(ride.getName());
        ride.setPickupLocation(ride.getPickupLocation());
        ride.setPickupTime(ride.getPickupTime());
        ride.setCampus(ride.getCampus());
        ride.setSeatsAvailable(ride.getSeatsAvailable());
        ride.setStatus(ride.getStatus());

        return ride;
    }
}

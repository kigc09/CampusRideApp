package com.example.campusrideapp.services;

import com.example.campusrideapp.api.RidePatchRequestDTO;
import com.example.campusrideapp.data.RideRepository;
import com.example.campusrideapp.data.UserRepository;
import com.example.campusrideapp.domains.Campus;
import com.example.campusrideapp.domains.Ride;
import com.example.campusrideapp.domains.Status;
import com.example.campusrideapp.domains.User;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class RideService {
    private static final Logger logger = LoggerFactory.getLogger(RideService.class);
    private final RideRepository rideRepo;
    private final UserRepository userRepo;

    public RideService(RideRepository rr, UserRepository ur){
        rideRepo = rr;
        userRepo = ur;
    }

    @Transactional
    public Ride registerNewRide(Ride ride, String username){
        /**if(ride.hasImage()){
         String imageName = ride.getImage().getImageName();
         imageName = ride.getName() + imageName.substring(imageName.lastIndexOf("."));
         ride.getImage().setImageName(imageName);
         }**/
        ride.setStatus(Status.AVAILABLE);
        ride = rideRepo.save(ride);

        return ride;
    }

    public Optional<Ride> getRideById(UUID id){ return rideRepo.findById(id); }

    public Page<Ride> getAllRides(Pageable pageable){
        return rideRepo.findAll(pageable);
    }

    public Page<Ride> searchRides(Campus campus, Pageable pageable){
        if (campus != null){
            return  rideRepo.findByCampus(campus, pageable);
        } else{
            return rideRepo.findAll(pageable);
        }
    }

    @Transactional
    @PreAuthorize("hasAnyRole('PASSENGER', 'BOTH') ")
    public boolean joinRide(UUID id, String username){
        Ride ride = rideRepo.findById(id).orElseThrow(() -> new RuntimeException("Ride was not found."));
        User user = userRepo.findByUsername(username).orElseThrow(() -> new RuntimeException("User was not found."));

        boolean alreadyJoined = ride.getPassengers().stream().anyMatch(passenger -> passenger.getId().equals(user.getId()));

        if (alreadyJoined){
            return false;
        }

        if(ride.getStatus() != Status.AVAILABLE){
            return false;
        }

        if(ride.getSeatsAvailable() <= 0){
            return false;
        }

        ride.setSeatsAvailable(ride.getSeatsAvailable() - 1);

        if(ride.getSeatsAvailable() == 0){
            ride.setStatus(Status.FULL);
        }

        ride.getPassengers().add(user);
        ride.setSeatsAvailable(ride.getSeatsAvailable()-1);

        rideRepo.save(ride);
        return true;
    }

    public boolean hasUserJoined(UUID rideId, String username){
        Ride ride = rideRepo.findById(rideId).orElseThrow(() -> new RuntimeException("Ride not found"));

        return ride.getPassengers().stream().anyMatch(passenger -> passenger.getUsername().equals(username));
    }

    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN','DRIVER', 'BOTH')")
    public boolean cancelRide(UUID id){
        Optional<Ride> optionalRide = rideRepo.findById(id);
        if (optionalRide.isEmpty()){
            return false;
        }

        Ride ride = optionalRide.get();

        if(ride.getStatus() == Status.COMPLETED ||
                ride.getStatus() == Status.CANCELLED){
            return false;
        }
        ride.setStatus(Status.CANCELLED);
        rideRepo.save(ride);
        return true;
    }

    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN','DRIVER', 'BOTH') ")
    public void updateRide(UUID id, Ride modifyRide){
        Ride modifyExistingRide = rideRepo.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Ride has not been found."));

        modifyExistingRide.setName(modifyRide.getName());
        modifyExistingRide.setPickupLocation(modifyRide.getPickupLocation());
        modifyExistingRide.setPickupTime(modifyRide.getPickupTime());
        modifyExistingRide.setSeatsAvailable(modifyRide.getSeatsAvailable());
        rideRepo.save(modifyExistingRide);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN') or hasRole('MNGR')")
    public Optional<Ride> patchUpdate(UUID id, RidePatchRequestDTO requestDTO){
        Optional<Ride> lookupItem = rideRepo.findById(id);

        if(lookupItem.isEmpty()){
            return Optional.empty();
        }

        Ride ride = lookupItem.get();

        if (requestDTO.getName() != null){
            ride.setName(requestDTO.getName());
        }
        if (requestDTO.getPickupLocation() != null){
            ride.setPickupLocation(requestDTO.getPickupLocation());
        }

        if (requestDTO.getPickupTime() != null){
            ride.setPickupTime(requestDTO.getPickupTime());
        }
        if (requestDTO.getCampus() != null){
            ride.setCampus(requestDTO.getCampus());
        }
        if (requestDTO.getSeatsAvailable() != 0){
            ride.setSeatsAvailable(requestDTO.getSeatsAvailable());
        }
        if (requestDTO.getStatus() != null){
            ride.setStatus(requestDTO.getStatus());
        }

        Ride updateItem = rideRepo.save(ride);

        return Optional.of(updateItem);
    }

    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN','DRIVER', 'BOTH') ")
    public boolean deleteRideById(UUID id){
        if(!rideRepo.existsById(id)){
            logger.info("Error, no ride with ID: {}", id);
            return false;
        }
        rideRepo.deleteById(id);
        logger.info("Ride deleted with ID: {}", id);
        return true;
    }
}

package com.example.campusrideapp.services;

import com.example.campusrideapp.data.RideRepository;
import com.example.campusrideapp.domains.Campus;
import com.example.campusrideapp.domains.Ride;
import com.example.campusrideapp.domains.Status;
import com.example.campusrideapp.domains.User;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class RideService {
    private static final Logger logger = LoggerFactory.getLogger(RideService.class);
    private final RideRepository rideRepo;

    public RideService(RideRepository rr){ rideRepo = rr;}

    @Transactional
    public Ride registerNewRide(Ride ride){
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

    public Map<UUID, Ride> getAllRides(){
        Map<UUID, Ride> db = new HashMap<>();
        rideRepo.findAll().forEach(ride -> db.put(ride.getId(), ride));
        return db;
    }

    public Map<UUID, Ride> searchRides(Campus campus, Status status){
        Map<UUID, Ride> db = new HashMap<>();

        boolean hasName = campus != null;
        boolean hasType = status != null;

        if (hasName && hasType){

            return getAllRides();
        }
        return db;
    }

    @Transactional
    public boolean joinRide(UUID id){
        Optional<Ride> optionalRide = rideRepo.findById(id);
        if (optionalRide.isEmpty()){
            return false;
        }

        Ride ride = optionalRide.get();

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

        rideRepo.save(ride);
        return true;
    }

    @Transactional
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

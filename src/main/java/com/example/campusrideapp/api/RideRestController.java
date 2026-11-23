package com.example.campusrideapp.api;

import com.example.campusrideapp.config.RideProperties;
import com.example.campusrideapp.domains.Campus;
import com.example.campusrideapp.domains.Ride;
import com.example.campusrideapp.services.RideService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/ride", produces = "application/json")
@CrossOrigin(origins = "http://localhost:3000")
public class RideRestController {
    private final RideService rideService;
    private final RideProperties rideProperties;

    public RideRestController(RideService rs, RideProperties rp) {
        rideService = rs;
        rideProperties = rp;
    }

    @GetMapping
    public Page<RideResponseDTO> getRides(@RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, rideProperties.getPageSize(),
                Sort.by("name").ascending().and(Sort.by("id")));

        return rideService.getAllRides(pageable).map(RideResponseDTO::from);
    }

    @GetMapping("/search")
    public Page<RideResponseDTO> searchRide(@RequestParam(required = false) Campus campus,
                                            @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, rideProperties.getPageSize(),
                Sort.by("name").ascending().and(Sort.by("id")));

        return rideService.searchRides(campus, pageable).map(RideResponseDTO::from);
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<RideResponseDTO> getRide(@PathVariable UUID id,
                                                   @AuthenticationPrincipal Jwt jwt) {
        Optional<Ride> ride = rideService.getRideById(id);

        //Convert String to pass through JWT

        if (ride.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        RideResponseDTO responseDTO = RideResponseDTO.from(ride.get());

        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping(consumes = "application/json")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<RideResponseDTO> createRide(@Valid @RequestBody RideCreateRequestDTO requestDTO,
                                                      @AuthenticationPrincipal Jwt jwt) {
        Ride ride = RideRequestMapper.fromCreate(requestDTO);

        String username = jwt.getSubject();

        Ride savedRide = rideService.registerNewRide(ride, username);

        RideResponseDTO response = RideResponseDTO.from(savedRide);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping(path = "/{id}", consumes = "application/json")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<RideResponseDTO> patchRide(@PathVariable UUID id,
                                                     @RequestBody RidePatchRequestDTO requestDTO) {
        Optional<Ride> updatedRide = rideService.patchUpdate(id, requestDTO);

        if (updatedRide.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        RideResponseDTO responseDTO = RideResponseDTO.from(updatedRide.get());

        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Void> deleteItem(@PathVariable UUID id) {
        boolean deleted = rideService.deleteRideById(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}

package com.example.campusrideapp.web;

import com.example.campusrideapp.config.RideProperties;
import com.example.campusrideapp.domains.Campus;
import com.example.campusrideapp.domains.Ride;
import com.example.campusrideapp.domains.Status;
import com.example.campusrideapp.services.RideService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/list")
public class RideListController {
    private static final Logger logger = LoggerFactory.getLogger(RideListController.class);
    private final RideService rideService;
    private final RideProperties rideProperties;

    public RideListController(RideService rs, RideProperties rp){
        rideService = rs;
        rideProperties = rp;}

    @ModelAttribute
    public void addRideStatusToModel(Model model){ model.addAttribute("Status", Status.values()); }

    @ModelAttribute("pageTitle")
    public String addPageTitle(){return "List Rides";}

    @GetMapping
    public String listRides(@RequestParam(defaultValue = "") Campus campus,
                            @RequestParam(required = false) Status status,
                            @RequestParam(defaultValue = "0") int page,
                            Model model){
        Pageable pageable = PageRequest.of(page, rideProperties.getPageSize(), Sort.by("name").ascending().and(Sort.by("id")));
        Page<Ride> rideDB = rideService.searchRides(campus, pageable);
        model.addAttribute("rideDB", rideDB);
        model.addAttribute("campus", campus);
        model.addAttribute("status", status);

        return "listRides";
    }

    @GetMapping("/list")
    public String joinRide(@PathVariable UUID id,
                           Model model,
                           Authentication authentication,
                            Pageable pageable){
        Map<UUID, Boolean> joinedRides = new HashMap<>();

        for(Ride ride : rideService.getAllRides(pageable)){
            boolean joined = rideService.hasUserJoined(
                    ride.getId(),
                    authentication.getName());
            joinedRides.put(ride.getId(), joined);
        }

        model.addAttribute("joinedRides", joinedRides);
        model.addAttribute("rideDB", rideService.getAllRides(pageable));
        return "redirect:/view/current/" + id;
    }
}
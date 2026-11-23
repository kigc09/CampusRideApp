package com.example.campusrideapp.web;

import com.example.campusrideapp.domains.Campus;
import com.example.campusrideapp.domains.Ride;
import com.example.campusrideapp.domains.RideDB;
import com.example.campusrideapp.domains.Status;
import com.example.campusrideapp.services.RideService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/view")
public class RideViewController {
    private static final Logger logger = LoggerFactory.getLogger(RideViewController.class);
    private final RideService rideService;

    public RideViewController(RideService rs){ rideService= rs; }

    @Autowired
    private RideDB rideDB;

    @GetMapping("/current/{id}")
    public String viewRides(@PathVariable UUID id, Model model, Authentication authentication){
        Ride current = rideService.getRideById(id).orElseThrow(() -> new RuntimeException("Ride not found."));
        boolean alreadyJoined = false;

        if(authentication != null && authentication.isAuthenticated()){
            alreadyJoined = current.getPassengers().stream().anyMatch(passenger -> passenger.getUsername().equals(authentication.getName()));
        }

        model.addAttribute("current", current);
        model.addAttribute("alreadyJoined", alreadyJoined);

        return "viewRides";
    }

    @GetMapping("/current/{id}/edit")
    public String viewEditRideForm(@PathVariable UUID id, Model model){
        Optional<Ride> ride = rideService.getRideById(id);

        if(ride.isEmpty()){
            logger.debug("Ride with id: {} not found to view", id);
            return "redirect:/list";
        }

        Ride current = ride.get();

        model.addAttribute("ride", current);
        model.addAttribute("Campus", Campus.values());
        model.addAttribute("Status", Status.values());
        model.addAttribute("pageTitle", current.getName());

        return "rideModifyForm";
    }

    @PostMapping("/current/{id}/cancel")
    public String cancelRide(@PathVariable UUID id, RedirectAttributes redirectAttributes){
        boolean canceled = rideService.cancelRide(id);
        if(canceled){
            redirectAttributes.addFlashAttribute("message", "Ride cancelled successfully");
        } else {
            redirectAttributes.addFlashAttribute("message", "Ride cancel failed");
        }
        return "redirect:/view/current/" + id;
    }

    @PostMapping("/current/{id}/join")
    public String joinRide(@PathVariable UUID id,
                           Authentication authentication,
                           RedirectAttributes redirectAttributes){
        boolean joined = rideService.joinRide(id, authentication.getName());

        if(joined) {
            redirectAttributes.addFlashAttribute("message", "Joined Successfully");
        }else{
            redirectAttributes.addFlashAttribute("message", "Joined Failed");
        }

        return "redirect:/view/current/" +id;
    }

    @PostMapping("/current/{id}/edit")
    public String modifyRides(@PathVariable UUID id, @Valid Ride ride, Errors errors, Model model){
        logger.debug("Ride received {} for modification", ride);
        if(errors.hasErrors()){
            model.addAttribute("Campus", Campus.values());
            model.addAttribute("Status", Status.values());
            model.addAttribute("pageTitle", ride.getName());
            return "rideModifyForm";
        }

        rideService.updateRide(id, ride);
        return "redirect:/view/current/" + id;
    }

    @PostMapping("/current/{id}/delete")
    public String deleteItem(@PathVariable UUID id){
        rideService.deleteRideById(id);
        return "redirect:/list";
    }
}
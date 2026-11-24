package com.example.campusrideapp.services;

import com.example.campusrideapp.config.EmailProperties;
import com.example.campusrideapp.data.UserRepository;
import com.example.campusrideapp.messaging.CancelRideEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class CancelRideEmailService {
    private static final Logger logger = LoggerFactory.getLogger(CancelRideEmailService.class);
    private final JavaMailSender mailSender;
    private final UserRepository userRepository;
    private final EmailProperties emailProperties;

    public CancelRideEmailService(JavaMailSender jms, UserRepository ur, EmailProperties ep){
        mailSender = jms;
        userRepository = ur;
        emailProperties = ep;
    }

    public void sendMessage(CancelRideEvent event){
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(emailProperties.getFromAddress());
        message.setTo(emailProperties.getFromAddress());

        message.setSubject("Cancelled: " + event.getStatus() + "," +
                "  " + event.getName());
        message.setText("Ride ID: " + event.getRideId() + "Item Name: " + event.getName() + "Inventory: " + event.getStatus());

        mailSender.send(message );
    }
}

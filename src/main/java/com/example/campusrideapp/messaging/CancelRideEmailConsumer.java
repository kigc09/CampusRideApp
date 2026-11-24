package com.example.campusrideapp.messaging;

import com.example.campusrideapp.services.CancelRideEmailService;
import org.springframework.kafka.annotation.KafkaListener;

public class CancelRideEmailConsumer {
    CancelRideEmailService emailService;

    public CancelRideEmailConsumer(CancelRideEmailService es){ emailService = es; }

    @KafkaListener(topics = "${KAFKA_RIDE_STATUS_TOPIC:ride-status-events}",
            groupId = "${KAFKA_EMAIL_GROUP:ride-email-notifications}")
    public void receive(CancelRideEvent event) { emailService.sendMessage(event);}
}


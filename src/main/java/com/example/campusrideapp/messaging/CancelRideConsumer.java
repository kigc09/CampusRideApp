package com.example.campusrideapp.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class CancelRideConsumer {
    private static final Logger logger = LoggerFactory.getLogger(CancelRideConsumer.class);

    @KafkaListener(topics = "${KAFKA_RIDE_STATUS_TOPIC:ride-status-events}",
            groupId = "${KAFKA_LOGGING_GROUP:ride-email-notifications} ")

    public void receive(CancelRideEvent event){
        //emailService.sendMessage(event);
        logger.info(
                "Ride: Ride id: {}, Name: {}, Campus: {}, Status: {}",
                event.getRideId(),
                event.getName(),
                event.getCampus(),
                event.getStatus()
        );
    }
}

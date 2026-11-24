package com.example.campusrideapp.messaging;

import com.example.campusrideapp.config.MessagingProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CancelRideEventProducer {
    private static final Logger logger = LoggerFactory.getLogger(CancelRideEventProducer.class);
    private final KafkaTemplate<String, CancelRideEvent> kafkaTemplate;
    private final MessagingProperties properties;

    public CancelRideEventProducer(KafkaTemplate<String, CancelRideEvent> template, MessagingProperties properties){
        kafkaTemplate = template;
        this.properties = properties;
    }

    public void send(CancelRideEvent event){
        kafkaTemplate.send(properties.getCancelRideTopic(), event.getRideId().toString(),event);
    }
}

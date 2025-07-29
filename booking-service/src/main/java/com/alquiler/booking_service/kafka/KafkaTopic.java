package com.alquiler.booking_service.kafka;

import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class KafkaTopic {

    private KafkaTemplate<String, Object> kafkaTemplate;

    private final String BOOKED_VEHICLE_TOPIC = "booked-vehicle-topic";

    public void ChangeStatusBookedVehicle(Object booked){
        kafkaTemplate.send(BOOKED_VEHICLE_TOPIC, booked);
    }

    private final String CANCELLED_VEHICLE_TOPIC = "cancelled-vehicle-topic";
    public void ChangeStatusCancelledVehicle(Object cancelled){
        kafkaTemplate.send(CANCELLED_VEHICLE_TOPIC, cancelled);
    }



}

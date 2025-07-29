package com.alquiler.gateway.kafkaConfig;

import com.alquiler.gateway.dto.RegisterDTO;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class KafkaTopic {

    private KafkaTemplate<String, Object> kafkaTemplate;

    private final String TOPIC_DATA_USER = "data-user-topic";

    public void sendDataUser(Object register) {
        kafkaTemplate.send(TOPIC_DATA_USER, register);
    }


}

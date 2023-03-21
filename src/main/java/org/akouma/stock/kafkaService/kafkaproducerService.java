package org.akouma.stock.kafkaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class kafkaproducerService {

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;
    private final String TOPIC = "kafkaTopic";

    public void sendMessage(String message) {
        this.kafkaTemplate.send(TOPIC, message);
    }
}

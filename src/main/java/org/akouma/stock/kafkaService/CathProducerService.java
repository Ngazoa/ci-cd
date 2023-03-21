package org.akouma.stock.kafkaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.akouma.stock.util.kakaConfig;

@Service
public class CathProducerService {
    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;
    private final String TOPIC =kakaConfig.cathTopic;

    public void sendMessage(String message) {
        this.kafkaTemplate.send(TOPIC, message);
    }
}

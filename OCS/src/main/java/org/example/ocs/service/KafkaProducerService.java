package org.example.ocs.service;

import lombok.RequiredArgsConstructor;
import org.example.ocs.model.OsrmApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {
    private final KafkaTemplate<String, OsrmApiResponse> kafkaTemplate;
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    public void sendMessage(String topic, OsrmApiResponse response) {
        kafkaTemplate.send(topic, response);
        logger.info("Sent OSRM response to {}: {}", topic, response);
    }
}
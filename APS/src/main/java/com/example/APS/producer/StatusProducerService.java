package com.example.APS.producer;

import com.example.APS.service.AlgoPlannerClient;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class StatusProducerService {

    private static final Logger logger = LoggerFactory.getLogger(StatusProducerService.class);
    private final AlgoPlannerClient algoPlannerClient;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public StatusProducerService(AlgoPlannerClient algoPlannerClient, KafkaTemplate<String, String> kafkaTemplate) {
        this.algoPlannerClient = algoPlannerClient;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(fixedDelayString = "${algoplanner.status.poll.interval}")
    public void fetchAndPublishStatuses() {
        String statusesJson = algoPlannerClient.fetchStatusesFromAlgoPlanner();
        if (statusesJson != null) {
            kafkaTemplate.send("status_updates_topic", statusesJson);
            logger.info("✅ Wysłano statusy paczek do Kafka: " + statusesJson);
        } else {
            logger.warn("⚠️ Brak statusów do wysłania.");
        }
    }
}
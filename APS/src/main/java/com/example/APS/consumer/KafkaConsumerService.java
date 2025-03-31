package com.example.APS.consumer;

import com.example.APS.service.AlgoPlannerClient;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;

import java.util.Map;
import java.util.UUID;

@Service
public class KafkaConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);
    private final AlgoPlannerClient algoPlannerClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public KafkaConsumerService(AlgoPlannerClient algoPlannerClient) {
        this.algoPlannerClient = algoPlannerClient;
    }

    @PostConstruct
    public void init() {
        System.out.println("🟢 KafkaConsumerService zainicjalizowany i gotowy do odbioru wiadomości.");
    }

    @KafkaListener(topics = {"status_updates_topic"}, groupId = "aps_group_debug456")
    public void consumeStatusUpdate(String statusJson) {
        logger.info("📥 Otrzymano status z Kafka: " + statusJson);
        try {
            boolean success = algoPlannerClient.sendStatusToAlgoPlanner(statusJson);
            if (success) {
                logger.info("✅ Status wysłany do AlgoPlanner: " + statusJson);
            } else {
                logger.error("❌ Nie udało się wysłać statusu do AlgoPlanner.");
            }
        } catch (Exception e) {
            logger.error("❌ Błąd parsowania JSON: ", e);
        }
    }

    public void consumeOrder(String orderJson) {
        logger.info("📥 Otrzymano zamówienie z Kafka do wysłania do AlgoPlanner: " + orderJson);

        try {
            // Generujemy `importBatchId` dla każdej grupy zamówień
            String importBatchId = UUID.randomUUID().toString();

            // Mapowanie JSON na obiekt Map
            Map<String, Object> orderMap = objectMapper.readValue(orderJson, Map.class);

            // Tworzymy poprawny JSON dla API AlgoPlanner
            String orderPayload = objectMapper.writeValueAsString(new Map[]{orderMap});

            boolean success = algoPlannerClient.sendOrderToAlgoPlanner(importBatchId, orderPayload);
            if (success) {
                logger.info("✅ Zamówienie wysłane do AlgoPlanner: " + orderJson);
            } else {
                logger.error("❌ Nie udało się wysłać zamówienia do AlgoPlanner.");
            }
        } catch (Exception e) {
            logger.error("❌ Błąd parsowania JSON: ", e);
        }
    }
}
package org.example.ocs.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.ocs.model.OsrmRequest;
import org.example.ocs.service.OsrmService;
import org.example.ocs.service.KafkaProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class OsrmKafkaConsumer {
    private final OsrmService osrmService;
    private final KafkaProducerService kafkaProducerService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final Logger logger = LoggerFactory.getLogger(OsrmKafkaConsumer.class);

    @KafkaListener(topics = "oms.osrm.routes.in", groupId = "oms.osrm.client", containerFactory = "kafkaListenerContainerFactory")
    public void consume(OsrmRequest request) {
        logger.info("Received OSRM request: {}", request);

        osrmService.getRoute(request).subscribe(response -> {
            kafkaProducerService.sendMessage("oms.osrm.routes.out", response);
        });
    }
}
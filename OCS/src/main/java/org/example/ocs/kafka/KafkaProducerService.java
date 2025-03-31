package org.example.ocs.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.ocs.model.RouteResponse;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, RouteResponse> kafkaTemplate;

    public void send(RouteResponse response) {
        log.info("📤 Wysyłam odpowiedź do Kafka: {}", response);
        kafkaTemplate.send("oms.osrm.route.response", response);
    }
}
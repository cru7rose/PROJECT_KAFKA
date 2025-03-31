package org.example.ocs.kafka;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.ocs.model.RouteRequest;
import org.example.ocs.service.OsrmService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final OsrmService osrmService;

    @KafkaListener(topics = "oms.osrm.route.request", groupId = "oms.osrm.client")
    public void consume(RouteRequest request) {
        log.info("📥 Odebrano żądanie trasy: {}", request);
        osrmService.handleRouteRequest(request);
    }
}
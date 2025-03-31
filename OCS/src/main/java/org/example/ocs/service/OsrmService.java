package org.example.ocs.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.ocs.kafka.KafkaProducerService;
import org.example.ocs.model.RouteRequest;
import org.example.ocs.model.RouteResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class OsrmService {

    private final KafkaProducerService producer;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    public void handleRouteRequest(RouteRequest request) {
        try {
            String url = "http://localhost:5000/route/v1/driving/"
                    + request.getFrom() + ";" + request.getTo()
                    + "?overview=false";

            log.info("🌍 Odpytuję OSRM: {}", url);
            String result = restTemplate.getForObject(url, String.class);
            JsonNode json = mapper.readTree(result);

            JsonNode route = json.get("routes").get(0);
            RouteResponse response = new RouteResponse();
            response.setDistance(route.get("distance").asDouble());
            response.setDuration(route.get("duration").asDouble());
            response.setWeight(route.get("weight").asDouble());

            producer.send(response);

        } catch (Exception e) {
            log.error("❌ Błąd podczas obsługi trasy: {}", e.getMessage(), e);
        }
    }
}
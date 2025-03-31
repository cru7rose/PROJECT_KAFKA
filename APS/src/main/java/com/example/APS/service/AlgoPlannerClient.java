package com.example.APS.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AlgoPlannerClient {

    private static final Logger logger = LoggerFactory.getLogger(AlgoPlannerClient.class);
    private final RestTemplate restTemplate = new RestTemplate();
    private final AlgoPlannerAuthService authService;

    @Value("${algoplanner.api.url}")
    private String apiUrl;

    public AlgoPlannerClient(AlgoPlannerAuthService authService, RestTemplate restTemplate) {
        this.authService = authService;
    }

    public boolean sendOrderToAlgoPlanner(String importBatchId, String orderJson) {
        try {
            String token = authService.getToken();
            if (token == null) {
                logger.error("❌ Nie udało się pobrać tokena, anulowanie wysyłki.");
                return false;
            }

            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            headers.set("Authorization", "Bearer " + token);

            HttpEntity<String> request = new HttpEntity<>(orderJson, headers);
            String requestUrl = apiUrl + "/Integration/Order/" + importBatchId;

            ResponseEntity<String> response = restTemplate.postForEntity(requestUrl, request, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                logger.info("✅ Wysłano zamówienie do AlgoPlanner: " + orderJson);
                return true;
            } else {
                logger.warn("⚠️ AlgoPlanner zwrócił kod: " + response.getStatusCode());
                return false;
            }
        } catch (Exception e) {
            logger.error("❌ Błąd wysyłania do AlgoPlanner: ", e);
            return false;
        }
    }

    public boolean sendStatusToAlgoPlanner(String statusJson) {
        try {
            String token = authService.getToken();
            if (token == null) {
                logger.error("❌ Nie udało się pobrać tokena, anulowanie wysyłki.");
                return false;
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(token);

            HttpEntity<String> request = new HttpEntity<>(statusJson, headers);
            String requestUrl = apiUrl + "/Integration/StatusUpdate";

            ResponseEntity<String> response = restTemplate.postForEntity(requestUrl, request, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                logger.info("✅ Wysłano status do AlgoPlanner: " + statusJson);
                return true;
            } else {
                logger.warn("⚠️ AlgoPlanner zwrócił kod: " + response.getStatusCode());
                return false;
            }
        } catch (Exception e) {
            logger.error("❌ Błąd wysyłania do AlgoPlanner: ", e);
            return false;
        }
    }

    public String fetchStatusesFromAlgoPlanner() {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(apiUrl + "/statuses", String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                logger.info("📥 Otrzymano statusy z AlgoPlanner: " + response.getBody());
                return response.getBody();
            } else {
                logger.warn("⚠️ AlgoPlanner zwrócił kod: " + response.getStatusCode());
                return null;
            }
        } catch (Exception e) {
            logger.error("❌ Błąd pobierania statusów z AlgoPlanner: " + e.getMessage());
            return null;
        }
    }
}
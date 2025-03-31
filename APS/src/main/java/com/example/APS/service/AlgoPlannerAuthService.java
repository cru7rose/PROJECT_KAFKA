package com.example.APS.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@Service
public class AlgoPlannerAuthService {

    private static final Logger logger = LoggerFactory.getLogger(AlgoPlannerAuthService.class);
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${algoplanner.auth.url}")
    private String authUrl;

    @Value("${algoplanner.auth.username}")
    private String username;

    @Value("${algoplanner.auth.password}")
    private String password;

    private String currentToken;
    private long tokenExpirationTime = 0;

    public String getToken() {
        if (currentToken != null && System.currentTimeMillis() < tokenExpirationTime) {
            return currentToken;
        }

        try {
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("username", username);
            requestBody.put("password", password);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(authUrl, request, Map.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Map<String, Object> responseBody = response.getBody();
                logger.info("🟢 Odpowiedź logowania AlgoPlanner: {}", responseBody);

                currentToken = (String) responseBody.get("AccessToken");
                Object expiresObj = responseBody.get("expires_in");
                int expiresIn = expiresObj != null ? ((Number) expiresObj).intValue() : 3600;

                tokenExpirationTime = System.currentTimeMillis() + (expiresIn * 1000) - 5000;

                logger.info("✅ Pobrano nowy token dla AlgoPlanner.");
                return currentToken;
            } else {
                logger.error("❌ Błąd pobierania tokena: " + response.getStatusCode());
            }
        } catch (Exception e) {
            logger.error("❌ Wyjątek podczas pobierania tokena: ", e);
        }
        return null;
    }
}
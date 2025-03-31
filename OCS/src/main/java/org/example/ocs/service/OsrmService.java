package org.example.ocs.service;

import lombok.RequiredArgsConstructor;
import org.example.ocs.model.OsrmApiResponse;
import org.example.ocs.model.OsrmRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class OsrmService {
    private final WebClient.Builder webClientBuilder;

    @Value("${osrm.base-url}")
    private String osrmBaseUrl;

    public Mono<OsrmApiResponse> getRoute(OsrmRequest request) {
        return webClientBuilder.build()
                .get()
                .uri(osrmBaseUrl + "/route/v1/{profile}/{coordinates}?overview=false", request.getProfile(), request.getCoordinates())
                .retrieve()
                .bodyToMono(OsrmApiResponse.class);
    }
}
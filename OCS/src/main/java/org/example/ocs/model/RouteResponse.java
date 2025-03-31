package org.example.ocs.model;

import lombok.Data;

@Data
public class RouteResponse {
    private double distance;
    private double duration;
    private double weight;
}
package org.example.ocs.model;

import lombok.Data;

@Data
public class RouteRequest {
    private String from; // format: "21.0122,52.2297"
    private String to;   // format: "20.9986,52.2371"
}
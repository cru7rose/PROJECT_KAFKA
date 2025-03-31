package org.example.ocs.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OsrmRequest {
    @NotBlank
    private String coordinates; // np. "11.588,48.139;11.587,48.140"

    @NotBlank
    private String profile;     // np. "driving", "walking"
}
package edu.unifalmg.monolithecommerce.shared.infraestructure.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
        String message,
        Map<String, String> error
) {
    public ErrorResponse(String message) {
        this(message, null);
    }
}

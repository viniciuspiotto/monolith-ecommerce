package edu.unifalmg.monolithecommerce.catalog.application.dto.response;

public record FileStorageResponse(
        String filename,
        String url,
        String type
) {}

package edu.unifalmg.monolithecommerce.catalog.application.dto.response;

public record FileStorageResponse(
        String uniqueName,
        String filename,
        String url,
        String type
) {}

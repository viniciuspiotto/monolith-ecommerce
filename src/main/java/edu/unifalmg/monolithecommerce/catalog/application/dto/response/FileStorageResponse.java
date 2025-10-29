package edu.unifalmg.monolithecommerce.catalog.application.dto.response;

public record FileStorageResponse(
        String originalFilename,
        String publicUrl,
        String mimeType
) {}

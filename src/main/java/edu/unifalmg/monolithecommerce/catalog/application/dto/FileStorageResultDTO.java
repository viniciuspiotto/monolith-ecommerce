package edu.unifalmg.monolithecommerce.catalog.application.dto;

public record FileStorageResultDTO(
        String originalFilename,
        String publicUrl,
        String mimeType
) {}

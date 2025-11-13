package edu.unifalmg.monolithecommerce.catalog.application.dto;

public record FileStorageDTO(
        String uniqueName,
        String filename,
        String url,
        String type
) {}

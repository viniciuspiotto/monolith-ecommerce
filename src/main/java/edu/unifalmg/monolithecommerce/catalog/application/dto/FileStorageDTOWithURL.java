package edu.unifalmg.monolithecommerce.catalog.application.dto;

public record FileStorageDTOWithURL(
        String uniqueName,
        String filename,
        String type,
        String url
) {}

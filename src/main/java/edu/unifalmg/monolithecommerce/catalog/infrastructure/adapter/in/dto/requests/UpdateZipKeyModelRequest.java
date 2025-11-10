package edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.in.dto.requests;

import jakarta.validation.constraints.NotBlank;

public record UpdateZipKeyModelRequest(
        @NotBlank
        String zipFileKey
) {}

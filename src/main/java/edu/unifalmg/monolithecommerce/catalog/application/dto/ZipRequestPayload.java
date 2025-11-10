package edu.unifalmg.monolithecommerce.catalog.application.dto;

import java.util.Map;
import java.util.UUID;

public record ZipRequestPayload(
        UUID modelId,
        Map<String, ModelTypeFile> modelS3Keys
) {
    public enum ModelTypeFile {
        TEXTURE,
        MESH
    }
}

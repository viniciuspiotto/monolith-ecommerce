package edu.unifalmg.monolithecommerce.catalog.application.dto;

import edu.unifalmg.monolithecommerce.catalog.domain.model.enums.ModelStatus;
import edu.unifalmg.monolithecommerce.shared.domain.model.Money;

import java.util.List;
import java.util.UUID;

public record ModelDTO (
        UUID modelId,
        String title,
        String description,
        FileDTO thumbnail,
        Money price,
        UUID categoryId,
        double averageRate,
        ModelStatus status,
        List<FileDTO> meshes,
        List<FileDTO> textures) {

    public record FileDTO(
            String filename,
            String url,
            String type
    ) {}
}
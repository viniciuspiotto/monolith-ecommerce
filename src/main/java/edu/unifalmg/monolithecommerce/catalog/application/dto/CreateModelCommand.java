package edu.unifalmg.monolithecommerce.catalog.application.dto;

import edu.unifalmg.monolithecommerce.shared.domain.model.Money;

import java.io.InputStream;
import java.util.UUID;

public record CreateModelCommand(
        String title,
        String description,
        FileCommand thumbnail,
        Money price,
        FileCommand meshFile,
        FileCommand textureFile,
        UUID categoryId
) {
    public record FileCommand(
            String originalFilename,
            InputStream contentStream
    ) {}
}

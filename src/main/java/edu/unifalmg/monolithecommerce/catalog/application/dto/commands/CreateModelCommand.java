package edu.unifalmg.monolithecommerce.catalog.application.dto.commands;

import edu.unifalmg.monolithecommerce.shared.domain.model.Money;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

public record CreateModelCommand(
        String title,
        String description,
        FileCommand thumbnailFile,
        Money price,
        List<FileCommand> meshFiles,
        List<FileCommand> textureFiles,
        UUID categoryId
) {
    public record FileCommand(
            String filename,
            InputStream contentStream
    ) {}
}

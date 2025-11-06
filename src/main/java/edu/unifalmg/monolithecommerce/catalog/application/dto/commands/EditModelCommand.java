package edu.unifalmg.monolithecommerce.catalog.application.dto.commands;

import edu.unifalmg.monolithecommerce.shared.domain.model.Money;

import java.util.List;
import java.util.UUID;

public record EditModelCommand(
        UUID id,
        String title,
        String description,
        CreateModelCommand.FileCommand thumbnailFile,
        Money price,
        List<String> meshFilenamesToRemove,
        List<CreateModelCommand.FileCommand> newMeshFiles,
        List<String> textureFilenamesToRemove,
        List<CreateModelCommand.FileCommand> newTextureFiles,
        UUID categoryId
){}

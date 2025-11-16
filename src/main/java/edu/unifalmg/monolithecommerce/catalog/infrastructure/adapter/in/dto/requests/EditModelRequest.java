package edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.in.dto.requests;

import edu.unifalmg.monolithecommerce.shared.infraestructure.web.validation.AtLeastOneFieldProvided;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@AtLeastOneFieldProvided
public record EditModelRequest(
        @Size(min = 1, max = 100)
        String title,

        String description,

        @PositiveOrZero
        BigDecimal price,

        UUID categoryId,

        MultipartFile thumbnailFile,

        List<String> meshFilenamesToRemove,
        List<MultipartFile> newMeshFiles,

        List<String> textureFilenamesToRemove,
        List<MultipartFile> newTextureFiles
) {
}
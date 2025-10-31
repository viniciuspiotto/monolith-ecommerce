package edu.unifalmg.monolithecommerce.catalog.application.dto.request;

import edu.unifalmg.monolithecommerce.shared.infraestructure.web.validation.AllFilesNotEmpty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record CreateModelRequest(
        @NotBlank(message = "Title is required")
        String title,

        @NotBlank(message = "Description is required")
        String description,

        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
        BigDecimal price,

        @NotNull(message = "Category ID is required")
        UUID categoryId,

        @NotNull(message = "Thumbnail file is required")
        @Size(min = 1, max = 1, message = "Exactly one thumbnail file must be provided")
        @AllFilesNotEmpty
        List<MultipartFile> thumbnailFile,

        @NotNull(message = "At least one mesh file is required")
        @Size(min = 1, message = "At least one mesh file is required")
        @AllFilesNotEmpty
        List<MultipartFile> meshFiles,

        @NotNull(message = "At least one texture file is required")
        @Size(min = 1, message = "At least one texture file is required")
        @AllFilesNotEmpty
        List<MultipartFile> textureFiles
) {
}

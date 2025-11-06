package edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.out.storage.local;

import edu.unifalmg.monolithecommerce.catalog.domain.model.enums.MeshType;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class MimeTypeValidationStrategy {

    public record ValidationResult(boolean isAllowed, String finalMimeType) {}

    public ValidationResult validate(String detectedMimeType, String originalFilename, Set<String> allowedMimeTypes) {

        String finalMimeType = detectedMimeType.toLowerCase();
        boolean isAllowed = allowedMimeTypes.contains(finalMimeType);

        if (!isAllowed &&
                (finalMimeType.equalsIgnoreCase("application/octet-stream") || // FBX
                        finalMimeType.equalsIgnoreCase("text/plain"))) {       // OBJ

            String extension = StorageFileUtils.getExtension(originalFilename).toLowerCase();

            if (extension.equals(".fbx") && allowedMimeTypes.contains(MeshType.FBX.getMimeType())) {
                isAllowed = true;
                finalMimeType = MeshType.FBX.getMimeType();

            } else if (extension.equals(".obj") && allowedMimeTypes.contains(MeshType.OBJ.getMimeType())) {
                isAllowed = true;
                finalMimeType = MeshType.OBJ.getMimeType();
            }
        }

        return new ValidationResult(isAllowed, finalMimeType);
    }
}

package edu.unifalmg.monolithecommerce.catalog.domain.model.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public enum MeshType {
    OBJ("model/obj"),
    FBX("application/vnd.autodesk.fbx");

    private final String mimeType;

    MeshType(String mimeType) {
        this.mimeType = mimeType;
    }

    public static final Set<String> ALLOWED_MIMETYPES = Arrays.stream(values())
            .map(MeshType::getMimeType)
            .collect(Collectors.toSet());

    public static MeshType fromMimeType(String mimeType) {
        if (mimeType == null || mimeType.isEmpty()) {
            throw new IllegalArgumentException("Mesh MimeType cannot be null or empty.");
        }

        return Arrays.stream(values())
                .filter(type -> type.mimeType.equalsIgnoreCase(mimeType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unsupported Mesh MimeType: " + mimeType
                ));
    }
}
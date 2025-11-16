package edu.unifalmg.monolithecommerce.catalog.domain.model.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public enum TextureType {
    PNG("image/png"),
    JPG("image/jpeg");

    private final String mimeType;

    TextureType(String mimeType) {
        this.mimeType = mimeType;
    }

    public static final Set<String> ALLOWED_MIMETYPES = Arrays.stream(values())
            .map(TextureType::getMimeType)
            .collect(Collectors.toSet());

    public static TextureType fromMimeType(String mimeType) {
        if (mimeType == null || mimeType.isEmpty()) {
            throw new IllegalArgumentException("Texture MimeType cannot be null or empty.");
        }

        return Arrays.stream(values())
                .filter(type -> type.mimeType.equalsIgnoreCase(mimeType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unsupported Texture MimeType: " + mimeType
                ));
    }
}
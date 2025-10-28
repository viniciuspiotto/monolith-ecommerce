package edu.unifalmg.monolithecommerce.catalog.domain.model.vo;

import edu.unifalmg.monolithecommerce.catalog.domain.model.enums.TextureType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Texture {

    private final String fileUrl;
    private final TextureType type;
    private final String originalFilename;
    private final String mimeType;

    public static Texture create(String fileUrl, String originalFilename, String detectedMimeType) {
        TextureType textureType = TextureType.fromMimeType(detectedMimeType);

        return new Texture(fileUrl, textureType, originalFilename, detectedMimeType);
    }
}

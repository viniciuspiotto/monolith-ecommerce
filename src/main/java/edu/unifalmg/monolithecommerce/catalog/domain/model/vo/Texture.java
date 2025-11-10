package edu.unifalmg.monolithecommerce.catalog.domain.model.vo;

import edu.unifalmg.monolithecommerce.catalog.domain.model.enums.TextureType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Texture {
    private final String uniqueName;
    private final TextureType type;
    private final String filename;

    public static Texture create(String uniqueName,  String filename, String detectedMimeType) {
        TextureType textureType = TextureType.fromMimeType(detectedMimeType);

        return new Texture(uniqueName, textureType, filename);
    }
}

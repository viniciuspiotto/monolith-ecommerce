package edu.unifalmg.monolithecommerce.catalog.domain.model.vo;

import edu.unifalmg.monolithecommerce.catalog.domain.model.enums.ThumbnailType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Thumbnail {

    private final String fileUrl;
    private final ThumbnailType type;
    private final String originalFilename;
    private final String mimeType;

    public static Thumbnail create(String fileUrl, String originalFilename, String detectedMimeType) {
        ThumbnailType thumbnailType = ThumbnailType.fromMimeType(detectedMimeType);

        return new Thumbnail(fileUrl, thumbnailType, originalFilename, detectedMimeType);
    }
}

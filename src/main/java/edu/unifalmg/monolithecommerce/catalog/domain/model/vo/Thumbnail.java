package edu.unifalmg.monolithecommerce.catalog.domain.model.vo;

import edu.unifalmg.monolithecommerce.catalog.domain.model.enums.ThumbnailType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Thumbnail {
    private final String url;
    private final ThumbnailType type;
    private final String filename;

    public static Thumbnail create(String url, String filename, String detectedMimeType) {
        ThumbnailType thumbnailType = ThumbnailType.fromMimeType(detectedMimeType);

        return new Thumbnail(url, thumbnailType, filename);
    }
}

package edu.unifalmg.monolithecommerce.catalog.domain.model.vo;

import edu.unifalmg.monolithecommerce.catalog.domain.model.enums.MeshType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Mesh {
    private final String fileUrl;
    private final MeshType type;
    private final String originalFilename;
    private final String mimeType;

    public static Mesh create(String fileUrl, String originalFilename, String detectedMimeType) {
        MeshType meshType = MeshType.fromMimeType(detectedMimeType);

        return new Mesh(fileUrl, meshType, originalFilename, detectedMimeType);
    }
}
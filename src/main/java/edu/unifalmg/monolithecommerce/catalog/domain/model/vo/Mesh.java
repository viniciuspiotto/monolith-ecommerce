package edu.unifalmg.monolithecommerce.catalog.domain.model.vo;

import edu.unifalmg.monolithecommerce.catalog.domain.model.enums.MeshType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Mesh {
    private final String uniqueName;
    private final String url;
    private final MeshType type;
    private final String filename;

    public static Mesh create(String uniqueName, String url, String filename, String detectedMimeType) {
        MeshType meshType = MeshType.fromMimeType(detectedMimeType);

        return new Mesh(uniqueName, url, meshType, filename);
    }
}
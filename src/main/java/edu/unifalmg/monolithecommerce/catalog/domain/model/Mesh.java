package edu.unifalmg.monolithecommerce.catalog.domain.model;

import lombok.Getter;

import java.util.EnumSet;

@Getter
public class Mesh {
    private static final EnumSet<MediaType> ALLOWED_TYPES = EnumSet.of(
            MediaType.FBX,
            MediaType.OBJ
    );

    private final String name;
    private final File file;

    private Mesh(String name, File file) {
        this.name = name;
        this.file = file;
    }

    public static Mesh create(String name, File file) {
        if (file == null || !ALLOWED_TYPES.contains(file.mediaType())) {
            throw new IllegalArgumentException("Invalid file type for Mesh. Received: " +
                    (file != null ? file.mediaType() : "null") +
                    ". Allowed types: " + ALLOWED_TYPES);
        }

        return new Mesh(name, file);
    }
}
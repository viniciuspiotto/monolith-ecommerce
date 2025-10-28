package edu.unifalmg.monolithecommerce.catalog.domain.model;

import lombok.Getter;

import java.util.EnumSet;

@Getter
public class Texture {
    private static final EnumSet<MediaType> ALLOWED_TYPES = EnumSet.of(
            MediaType.PNG,
            MediaType.JPG
    );

    private final String name;
    private final File file;

    private Texture(String name, File file) {
        this.name = name;
        this.file = file;
    }

    public static Texture create(String name, File file) {
        if (file == null || !ALLOWED_TYPES.contains(file.mediaType())) {
            throw new IllegalArgumentException("Invalid file type for Texture. Received: " +
                    (file != null ? file.mediaType() : "null") +
                    ". Allowed types: " + ALLOWED_TYPES);
        }

        return new Texture(name, file);
    }
}

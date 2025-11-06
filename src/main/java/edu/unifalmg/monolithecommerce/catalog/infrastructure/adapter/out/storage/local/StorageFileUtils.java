package edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.out.storage.local;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class StorageFileUtils {

    public static String getExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf("."));
    }
}

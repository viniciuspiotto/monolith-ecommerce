package edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.storage;

import edu.unifalmg.monolithecommerce.catalog.application.dto.commands.CreateModelCommand;
import edu.unifalmg.monolithecommerce.catalog.application.dto.response.FileStorageResponse;
import edu.unifalmg.monolithecommerce.catalog.application.port.out.FileStoragePort;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Set;
import java.util.UUID;

@Component
public class LocalStorageFileAdapter implements FileStoragePort {

    private final Path rootLocation;
    private final String baseUrl;
    private static final Tika TIKA_DETECTOR = new Tika();

    public LocalStorageFileAdapter(
            @Value("${storage.local.path:./uploads/files}") String path,
            @Value("${storage.local.base-url:http://localhost:8080/files/") String baseUrl
    ) {
        this.rootLocation = Paths.get(path);
        this.baseUrl = baseUrl;

        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage location", e);
        }
    }

    @Override
    public FileStorageResponse save(CreateModelCommand.FileCommand cmd, Set<String> allowedMimeTypes) {
        try (InputStream inputStream = new BufferedInputStream(cmd.contentStream())) {
            inputStream.mark(1024 * 1024);
            String detectedMimeType = TIKA_DETECTOR.detect(inputStream);
            inputStream.reset();

            if (detectedMimeType == null || !allowedMimeTypes.contains(detectedMimeType.toLowerCase())) {
                throw new IllegalArgumentException(
                        "Invalid file type. Only " + allowedMimeTypes + " are allowed. Detected: " + detectedMimeType
                );
            }

            String extension = getExtension(cmd.originalFilename());
            String uniqueFilename = UUID.randomUUID() + extension;
            Path destination = rootLocation.resolve(uniqueFilename);

            Files.copy(inputStream, destination, StandardCopyOption.REPLACE_EXISTING);

            String publicUrl = this.baseUrl + uniqueFilename;

            new StorageTransactionSynchronization(destination).register();

            return new FileStorageResponse(
                    cmd.originalFilename(),
                    publicUrl,
                    detectedMimeType
            );
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file.", e);
        }
    }

    private String getExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf("."));
    }
}

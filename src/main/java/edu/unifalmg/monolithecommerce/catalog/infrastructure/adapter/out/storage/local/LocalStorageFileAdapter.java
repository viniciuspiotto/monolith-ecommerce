package edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.out.storage.local;

import edu.unifalmg.monolithecommerce.catalog.application.dto.FileStorageDTO;
import edu.unifalmg.monolithecommerce.catalog.application.dto.commands.CreateModelCommand;
import edu.unifalmg.monolithecommerce.catalog.application.port.out.FileStoragePort;
import edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.out.storage.utils.MimeTypeValidationStrategy;
import edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.out.storage.utils.StorageFileUtils;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
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
    private final MimeTypeValidationStrategy validationStrategy;

    public LocalStorageFileAdapter(
            @Value("${storage.local.path:uploads/files}") String path,
            @Value("${storage.local.base-url:http://localhost:8080/files/}") String baseUrl,
            MimeTypeValidationStrategy validationStrategy
    ) {
        this.rootLocation = Paths.get(path);
        this.baseUrl = baseUrl;
        this.validationStrategy = validationStrategy;

        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage location", e);
        }
    }

    @Override
    public FileStorageDTO save(CreateModelCommand.FileCommand cmd, Set<String> allowedMimeTypes, boolean isPublic) {
        try (InputStream inputStream = new BufferedInputStream(cmd.contentStream())) {
            inputStream.mark(1024 * 1024);

            String detectedMimeType = TIKA_DETECTOR.detect(inputStream);
            inputStream.reset();

            MimeTypeValidationStrategy.ValidationResult validation = validationStrategy.validate(
                    detectedMimeType,
                    cmd.filename(),
                    allowedMimeTypes
            );

            if (!validation.isAllowed()) {
                throw new IllegalArgumentException(
                        "Invalid file type. Allowed: " + allowedMimeTypes +
                                ". Detected: " + detectedMimeType +
                                ". Filename: " + cmd.filename()
                );
            }

            String extension = StorageFileUtils.getExtension(cmd.filename());
            String uniqueFilename = UUID.randomUUID() + extension;
            Path destination = rootLocation.resolve(uniqueFilename);

            Files.copy(inputStream, destination, StandardCopyOption.REPLACE_EXISTING);

//            String publicUrl = this.baseUrl + uniqueFilename;

            new StorageTransactionSynchronization(destination).register();

            return new FileStorageDTO(
                    uniqueFilename,
                    cmd.filename(),
                    "",
                    validation.finalMimeType()
            );
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file.", e);
        }
    }

    @Override
    public void delete(String uniqueName) {
        if (uniqueName == null || uniqueName.isBlank()) {
            return;
        }

        try {
            Path fileToDelete = this.rootLocation.resolve(uniqueName).normalize();

            if (!fileToDelete.startsWith(this.rootLocation)) {
                throw new IllegalArgumentException("Invalid filename. Path traversal attempt detected.");
            }

            Files.deleteIfExists(fileToDelete);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file: " + uniqueName, e);
        }
    }

    @Override
    public URL generateUrl(String filename) {
        if (filename == null || filename.isBlank()) {
            return null;
        }

        try {
            String fullUrl = this.baseUrl + filename;
            return URI.create(fullUrl).toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException("Failed to generate URL. Check 'storage.local.base-url' configuration.", e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Failed to generate URL due to invalid syntax: " + this.baseUrl + filename, e);
        }
    }
}

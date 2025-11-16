package edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.out.storage.s3;

import edu.unifalmg.monolithecommerce.catalog.application.dto.FileStorageDTO;
import edu.unifalmg.monolithecommerce.catalog.application.dto.commands.CreateModelCommand;
import edu.unifalmg.monolithecommerce.catalog.application.port.out.FileStoragePort;
import edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.out.storage.utils.MimeTypeValidationStrategy;
import edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.out.storage.utils.StorageFileUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.util.Set;
import java.util.UUID;

@Component
@Log4j2
public class S3FileStorageAdapter implements FileStoragePort {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;
    private final MimeTypeValidationStrategy validationStrategy;
    private final String s3BucketName;
    private final String region;

    private static final Tika TIKA_DETECTOR = new Tika();


    public S3FileStorageAdapter(
            S3Client s3Client,
            S3Presigner s3Presigner,
            MimeTypeValidationStrategy validationStrategy,
            @Value("${aws.bucketName}") String s3BucketName,
            @Value("${aws.region}") String region
    ) {
        this.s3Client = s3Client;
        this.s3Presigner = s3Presigner;
        this.validationStrategy = validationStrategy;
        this.s3BucketName = s3BucketName;
        this.region = region;
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
                log.warn("Invalid file type uploaded. Allowed: {}, Detected: {}, Filename: {}",
                        allowedMimeTypes, detectedMimeType, cmd.filename());
                throw new IllegalArgumentException(
                        "Invalid file type. Allowed: " + allowedMimeTypes +
                                ". Detected: " + detectedMimeType +
                                ". Filename: " + cmd.filename()
                );
            }

            String extension = StorageFileUtils.getExtension(cmd.filename());
            String uniqueFilename = UUID.randomUUID() + extension;

            String key;
            String url = null;

            if (isPublic) {
                key = "thumbnails/" + uniqueFilename;
                url = "https://" + s3BucketName + ".s3." + region + ".amazonaws.com" + "/" + key;
            } else {
                key = uniqueFilename;
            }

            try {
                PutObjectRequest putRequest = PutObjectRequest.builder()
                        .bucket(this.s3BucketName)
                        .key(key)
                        .contentType(validation.finalMimeType())
                        .contentLength(cmd.size())
                        .build();

                s3Client.putObject(putRequest, RequestBody.fromInputStream(inputStream, cmd.size()));
                log.info("File stored successfully in S3. Bucket: {}, Key: {}", this.s3BucketName, uniqueFilename);
            } catch (SdkException e) {
                log.error("Failed to put object in S3. Bucket: {}, Key: {}", this.s3BucketName, uniqueFilename, e);
                throw new RuntimeException("Failed to store file in S3.", e);
            }

            return new FileStorageDTO(
                    uniqueFilename,
                    cmd.filename(),
                    url,
                    validation.finalMimeType()
            );

        } catch (IOException e) {
            log.error("Failed to read input stream for file storage.", e);
            throw new RuntimeException("Failed to store file.", e);
        }
    }

    @Override
    public void delete(String filename) {
        try {
            DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
                    .bucket(this.s3BucketName)
                    .key(filename)
                    .build();

            s3Client.deleteObject(deleteRequest);
            log.info("File deleted successfully from S3. Bucket: {}, Key: {}", this.s3BucketName, filename);

        } catch (SdkException e) {
            log.error("Failed to delete file from S3. Bucket: {}, Key: {}", this.s3BucketName, filename, e);
            throw new RuntimeException("Failed to delete file from S3.", e);
        }
    }

    @Override
    public URL generateUrl(String filename) {
        try {
            HeadObjectRequest headRequest = HeadObjectRequest.builder()
                    .bucket(this.s3BucketName)
                    .key(filename)
                    .build();

            s3Client.headObject(headRequest);
            log.debug("File confirmed to exist in S3. Bucket: {}, Key: {}", this.s3BucketName, filename);

            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(this.s3BucketName)
                    .key(filename)
                    .build();

            GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(10))
                    .getObjectRequest(getObjectRequest)
                    .build();

            PresignedGetObjectRequest presignedRequest = s3Presigner.presignGetObject(presignRequest);
            log.info("Generated presigned URL for S3 key: {}", filename);

            return presignedRequest.url();

        } catch (NoSuchKeyException e) {
            log.warn("Attempted to generate URL for non-existent file. Bucket: {}, Key: {}", this.s3BucketName, filename, e);
            throw new RuntimeException("File does not exist", e);
        } catch (SdkException e) {
            log.error("Failed to generate presigned URL for S3. Bucket: {}, Key: {}", this.s3BucketName, filename, e);
            throw new RuntimeException("Failed to generate URL for file.", e);
        }
    }
}
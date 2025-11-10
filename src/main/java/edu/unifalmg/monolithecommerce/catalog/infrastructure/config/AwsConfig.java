package edu.unifalmg.monolithecommerce.catalog.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.sqs.SqsClient;

@Configuration
public class AwsConfig {

    @Value("${aws.accessKeyId}")
    private String accessKeyId;

    @Value("${aws.secretKey}")
    private String accessKeySecret;

    @Bean
    public AwsCredentials awsCredentials() {
        return AwsBasicCredentials.create(accessKeyId, accessKeySecret);
    }

    @Bean
    public AwsCredentialsProvider awsCredentialsProvider(AwsCredentials credentials) {
        return StaticCredentialsProvider.create(credentials);
    }

    @Bean
    public S3Client amazonS3(
            AwsCredentialsProvider credentialsProvider,
            @Value("${aws.region}") String region
    ) {
        return S3Client.builder()
                .credentialsProvider(credentialsProvider)
                .region(Region.of(region))
                .build();
    }

    @Bean
    public SqsClient amazonSQS(
            AwsCredentialsProvider credentialsProvider,
            @Value("${aws.region}") String region
    ) {
        return SqsClient.builder()
                .credentialsProvider(credentialsProvider)
                .region(Region.of(region))
                .build();
    }

    @Bean
    public S3Presigner s3Presigner(
            AwsCredentialsProvider credentialsProvider,
            @Value("${aws.region}") String region
    ) {
        return S3Presigner.builder()
                .credentialsProvider(credentialsProvider)
                .region(Region.of(region))
                .build();
    }
}

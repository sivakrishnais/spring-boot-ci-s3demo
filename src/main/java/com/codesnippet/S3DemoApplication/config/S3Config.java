package com.codesnippet.S3DemoApplication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ClientBuilder;

@Configuration
public class S3Config {

    @Bean
    public S3Client s3Client() {
        // Read directly from environment variables — avoids the Spring
        // placeholder resolution failure when the env vars are absent.
        String accessKey = System.getenv("AWS_ACCESS_KEY");
        String secretKey = System.getenv("AWS_SECRET_KEY");
        String region    = System.getenv("AWS_REGION");

        if (region == null || region.isBlank()) {
            region = "us-east-1"; // sensible default
        }

        S3ClientBuilder builder = S3Client.builder()
                .region(Region.of(region));

        // Use static credentials only when both keys are present;
        // otherwise fall back to the Default Credentials Provider Chain
        // (AWS CLI profile ~/.aws/credentials, EC2 IAM role, ECS task role, etc.)
        if (accessKey != null && !accessKey.isBlank()
                && secretKey != null && !secretKey.isBlank()) {
            builder.credentialsProvider(
                    StaticCredentialsProvider.create(
                            AwsBasicCredentials.create(accessKey, secretKey)));
        } else {
            builder.credentialsProvider(DefaultCredentialsProvider.create());
        }

        return builder.build();
    }
}

package com.codesnippet.S3DemoApplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

@Service
public class S3Service {

    @Autowired
    private S3Client s3Client;

    // Read bucket name directly from env var to avoid Spring
    // placeholder resolution failure when AWS_BUCKET_NAME is unset.
    private final String bucketName;

    public S3Service() {
        String env = System.getenv("AWS_BUCKET_NAME");
        this.bucketName = (env != null && !env.isBlank()) ? env : "my-default-bucket";
    }

    public void uploadFile(MultipartFile file) throws IOException {
        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(file.getOriginalFilename())
                        .build(),
                RequestBody.fromBytes(file.getBytes()));
    }

    public byte[] downloadFile(String key) {
        ResponseBytes<GetObjectResponse> objectAsBytes =
                s3Client.getObjectAsBytes(
                        GetObjectRequest.builder()
                                .bucket(bucketName)
                                .key(key)
                                .build());
        return objectAsBytes.asByteArray();
    }
}

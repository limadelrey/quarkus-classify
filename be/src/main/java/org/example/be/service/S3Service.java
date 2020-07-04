package org.example.be.service;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@ApplicationScoped
public class S3Service {

    @ConfigProperty(name = "bucket.name")
    String bucketName;

    @Inject
    S3Client client;

    public String upload(UUID id,
                         InputStream data,
                         String mimeType) {
        // Upload file
        final PutObjectResponse putObjectResponse = client.putObject(
                buildPutObjectRequest(id, mimeType),
                RequestBody.fromFile(uploadToTemp(data))
        );

        if (putObjectResponse == null) {
            throw new IllegalArgumentException("Error uploading file");
        }

        // Get URL
        final GetUrlRequest getUrlRequest = buildGetUrlRequest(id);

        return client.utilities().getUrl(getUrlRequest).toString();
    }

    //

    private PutObjectRequest buildPutObjectRequest(UUID id,
                                                   String mimeType) {
        return PutObjectRequest.builder()
                .bucket(bucketName)
                .key(id.toString())
                .contentType(mimeType)
                .build();
    }

    private GetUrlRequest buildGetUrlRequest(UUID id) {
        return GetUrlRequest.builder()
                .bucket(bucketName)
                .key(id.toString())
                .build();
    }

    private File uploadToTemp(InputStream data) {
        final File tempPath;

        try {
            tempPath = File.createTempFile("uploadS3Tmp", ".tmp");
            Files.copy(data, tempPath.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return tempPath;
    }

}

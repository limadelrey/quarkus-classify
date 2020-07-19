package org.example.be.service;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.opentracing.Traced;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.File;
import java.util.UUID;

@Traced
@ApplicationScoped
public class S3Service {

    @ConfigProperty(name = "bucket.name")
    String bucketName;

    @Inject
    S3Client client;

    /**
     * Upload file to S3 bucket and return its URL
     *
     * @param id       Classification ID
     * @param file     Image file
     * @param mimeType Image media type
     * @return Image URL
     */
    public String upload(UUID id,
                         File file,
                         String mimeType) {
        // Upload file
        final PutObjectResponse putObjectResponse = client.putObject(
                buildPutObjectRequest(id, mimeType),
                RequestBody.fromFile(file)
        );

        if (putObjectResponse == null) {
            throw new NullPointerException("Error uploading file");
        }

        // Get URL
        final GetUrlRequest getUrlRequest = buildGetUrlRequest(id);

        return client.utilities().getUrl(getUrlRequest).toString();
    }

    /**
     * Uploads a new object to the specified Amazon S3 bucket
     *
     * @param id       Classification ID
     * @param mimeType Image media type
     * @return PutObjectRequest
     */
    private PutObjectRequest buildPutObjectRequest(UUID id,
                                                   String mimeType) {
        return PutObjectRequest.builder()
                .bucket(bucketName)
                .key(id.toString())
                .contentType(mimeType)
                .build();
    }

    /**
     * Request to generate a URL representing an object in Amazon S3
     *
     * @param id Classification ID
     * @return GetUrlRequest
     */
    private GetUrlRequest buildGetUrlRequest(UUID id) {
        return GetUrlRequest.builder()
                .bucket(bucketName)
                .key(id.toString())
                .build();
    }

}

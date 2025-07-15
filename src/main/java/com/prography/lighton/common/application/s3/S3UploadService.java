package com.prography.lighton.common.application.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class S3UploadService {

    private static final String DEFAULT_EXTENSION = ".png";
    private static final String SLASH = "/";

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadImageFile(MultipartFile image, String keyPrefix) {
        try {
            String contentType = image.getContentType();
            String originalFilename = image.getOriginalFilename();
            String extension = extractExtension(originalFilename);

            return uploadToS3(
                    new ByteArrayInputStream(image.getBytes()),
                    image.getSize(),
                    keyPrefix,
                    contentType,
                    extension
            );
        } catch (IOException e) {
            throw new ImageUploadFailedException();
        }
    }

    private String uploadToS3(InputStream inputStream, long contentLength, String keyPrefix, String contentType,
                              String extension) {
        String fileName = keyPrefix + SLASH + UUID.randomUUID() + extension;
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(contentType);
        metadata.setContentLength(contentLength);

        amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, metadata));
        return amazonS3.getUrl(bucket, fileName).toString();
    }

    private String extractExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return DEFAULT_EXTENSION;
        }
        return filename.substring(filename.lastIndexOf("."));
    }
}

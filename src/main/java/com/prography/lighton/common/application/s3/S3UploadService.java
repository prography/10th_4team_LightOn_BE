package com.prography.lighton.common.application.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.prography.lighton.member.common.domain.entity.Member;
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

    private static final String DEFAULT_EXTENSION = "";
    private static final String SLASH = "/";

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadFile(MultipartFile file, Member member) {
        String originalFilename = file.getOriginalFilename();
        String extension = extractExtension(originalFilename);
        String contentType = file.getContentType();

        try (InputStream is = file.getInputStream()) {
            long size = file.getSize();
            String key = member.getId() + SLASH + UUID.randomUUID() + extension;

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(contentType);
            metadata.setContentLength(size);
            amazonS3.putObject(new PutObjectRequest(bucket, key, is, metadata));
            return amazonS3.getUrl(bucket, key).toString();
        } catch (IOException e) {
            throw new S3UploadFailedException();
        }
    }

    private String extractExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return DEFAULT_EXTENSION;
        }
        return filename.substring(filename.lastIndexOf("."));
    }
}

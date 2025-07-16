package com.prography.lighton.common.application.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.prography.lighton.member.common.domain.entity.Member;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
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

    public void deleteFile(String fileUrl) {
        if (fileUrl == null || fileUrl.isBlank()) {
            return;
        }
        try {
            String key = extractKeyFromUrl(fileUrl);
            amazonS3.deleteObject(bucket, key);
        } catch (S3DeleteFailedException e) {
            log.warn("잘못된 S3 URL로 인해 삭제 대상에서 제외됨: {}", fileUrl, e);
        }
    }

    public void deleteFiles(Collection<String> fileUrls) {
        if (fileUrls == null || fileUrls.isEmpty()) {
            return;
        }

        List<DeleteObjectsRequest.KeyVersion> keys = fileUrls.stream()
                .filter(Objects::nonNull)
                .filter(s -> !s.isBlank())
                .map(url -> {
                    try {
                        return new DeleteObjectsRequest.KeyVersion(extractKeyFromUrl(url));
                    } catch (S3DeleteFailedException e) {
                        log.warn("잘못된 S3 URL로 인해 삭제 대상에서 제외됨: {}", url, e);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();

        if (keys.isEmpty()) {
            return;
        }

        DeleteObjectsRequest request = new DeleteObjectsRequest(bucket).withKeys(keys);
        amazonS3.deleteObjects(request);
    }

    private String extractExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return DEFAULT_EXTENSION;
        }
        return filename.substring(filename.lastIndexOf("."));
    }

    private String extractKeyFromUrl(String url) {
        try {
            URI uri = new URI(url);
            String path = uri.getPath();
            if (path == null || path.length() <= 1) {
                throw new S3DeleteFailedException("Invalid S3 URL: " + url);
            }
            return path.substring(1);
        } catch (URISyntaxException e) {
            throw new S3DeleteFailedException("Invalid S3 URL: " + url + "\n" + e);
        }
    }
}
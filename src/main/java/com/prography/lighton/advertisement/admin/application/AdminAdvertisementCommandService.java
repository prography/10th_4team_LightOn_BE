package com.prography.lighton.advertisement.admin.application;

import com.prography.lighton.advertisement.admin.infrastructure.AdminAdvertisementRepository;
import com.prography.lighton.advertisement.admin.presentation.dto.request.SaveAdvertisementRequestDTO;
import com.prography.lighton.advertisement.common.domain.entity.Advertisement;
import com.prography.lighton.common.application.s3.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminAdvertisementCommandService {

    private static final String IMAGE_PREFIX = "ads";

    private final AdminAdvertisementRepository advertisementRepository;
    private final S3UploadService uploadService;

    public void saveAdvertisement(SaveAdvertisementRequestDTO request, MultipartFile image) {
        String imageUrl = uploadService.uploadFile(image, IMAGE_PREFIX);

        Advertisement advertisement = Advertisement.of(imageUrl, request.position(), request.displayOrder(),
                request.linkUrl(), request.title(), request.content());
        advertisementRepository.save(advertisement);
    }

    public void deleteAdvertisement(Long advertisementId) {
        deleteAdvertisement(advertisementRepository.getById(advertisementId));
    }

    private void deleteAdvertisement(Advertisement advertisement) {
        uploadService.deleteFile(advertisement.getImageUrl());
        advertisementRepository.delete(advertisement);
    }
}

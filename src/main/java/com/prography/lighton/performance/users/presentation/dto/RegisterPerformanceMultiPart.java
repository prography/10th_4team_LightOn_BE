package com.prography.lighton.performance.users.presentation.dto;

import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

public record RegisterPerformanceMultiPart(
        @Valid PerformanceRegisterRequest data,
        MultipartFile posterImage,
        MultipartFile proof
) {
}

package com.prography.lighton.performance.users.presentation.dto;

import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

public record UpdatePerformanceMultiPart(
        @Valid SavePerformanceRequest data,
        MultipartFile posterImage,
        MultipartFile proof
) {
}

package com.prography.lighton.performance.users.presentation.dto.request;

import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

public record RegisterBuskingMultiPart(
        @Valid ArtistBuskingRegisterRequest data,
        MultipartFile posterImage,
        MultipartFile proof
) {
}

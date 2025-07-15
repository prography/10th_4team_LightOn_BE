package com.prography.lighton.artist.users.presentation.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record UpdateArtistMultipart(
        @Valid SaveArtistRequest data,
        @NotNull MultipartFile profileImage,
        @NotNull MultipartFile proof
) {
}

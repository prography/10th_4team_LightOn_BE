package com.prography.lighton.artist.users.presentation.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public record UpdateArtistMultipart(
        @Valid UpdateArtistRequest data,
        MultipartFile profileImage,
        @Size(max = 5) List<MultipartFile> activityPhotos
) {
}

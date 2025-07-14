package com.prography.lighton.artist.users.presentation.dto.response;

public record ArtistCheckResponseDTO(
        boolean isArtist
) {
    public static ArtistCheckResponseDTO of(boolean isArtist) {
        return new ArtistCheckResponseDTO(isArtist);
    }
}

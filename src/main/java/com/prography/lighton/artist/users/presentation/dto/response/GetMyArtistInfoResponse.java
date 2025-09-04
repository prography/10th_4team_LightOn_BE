package com.prography.lighton.artist.users.presentation.dto.response;

public record GetMyArtistInfoResponse(
        String artistName,
        String artistDescription
) {
    public static GetMyArtistInfoResponse of(String artistName, String artistDescription) {
        return new GetMyArtistInfoResponse(artistName, artistDescription);
    }
}

package com.prography.lighton.member.users.presentation.dto.response;

import com.prography.lighton.artist.common.domain.entity.Artist;
import java.util.List;

public record GetMyPreferredArtistsResponse(List<ArtistResponse> artists) {

    public static GetMyPreferredArtistsResponse of(List<Artist> artists) {
        return new GetMyPreferredArtistsResponse(
                artists.stream()
                        .map(ArtistResponse::of)
                        .toList());
    }

    private record ArtistResponse(
            long id,
            String name,
            String profileImageUrl
    ) {
        public static ArtistResponse of(Artist artist) {
            return new ArtistResponse(artist.getId(), artist.getStageName(), artist.getProfileImageUrl());
        }
    }
}

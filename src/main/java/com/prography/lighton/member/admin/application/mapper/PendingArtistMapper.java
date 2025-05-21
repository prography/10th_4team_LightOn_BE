package com.prography.lighton.member.admin.application.mapper;

import com.prography.lighton.artist.domain.entity.Artist;
import com.prography.lighton.artist.domain.entity.ArtistGenre;
import com.prography.lighton.genre.domain.entity.Genre;
import com.prography.lighton.member.admin.presentation.GetArtistApplicationDetailResponseDTO;
import com.prography.lighton.member.admin.presentation.GetArtistApplicationListResponseDTO.ArtistApplicationDTO;
import java.util.List;

public class PendingArtistMapper {

    public static ArtistApplicationDTO toPendingArtistDTO(Artist artist) {
        return ArtistApplicationDTO.of(
                artist.getId(),
                artist.getStageName(),
                artist.getApproveStatus(),
                artist.getCreatedAt(),
                toGenres(artist.getGenres()),
                artist.getProfileImageUrl()
        );
    }

    public static GetArtistApplicationDetailResponseDTO toPendingArtistDetailResponseDTO(Artist artist) {
        return GetArtistApplicationDetailResponseDTO.of(
                artist.getId(),
                artist.getApproveStatus(),
                artist.getStageName(),
                artist.getDescription(),
                artist.getActivityLocation().getSubRegion().getCode(),
                toGenres(artist.getGenres()),
                artist.getHistory().getBio(),
                artist.getHistory().getActivityImages().toList(),
                artist.getProofUrl()
        );
    }

    private static List<String> toGenres(List<ArtistGenre> artistGenres) {
        return artistGenres.stream()
                .map(ArtistGenre::getGenre)
                .map(Genre::getName)
                .toList();
    }
}

package com.prography.lighton.member.application.admin.mapper;

import com.prography.lighton.artist.domain.entity.Artist;
import com.prography.lighton.artist.domain.entity.ArtistGenre;
import com.prography.lighton.genre.domain.entity.Genre;
import com.prography.lighton.member.presentation.dto.response.GetPendingArtistDetailResponseDTO;
import com.prography.lighton.member.presentation.dto.response.GetPendingArtistListResponseDTO.PendingArtistDTO;
import java.util.List;

public class PendingArtistMapper {

    public static PendingArtistDTO toPendingArtistDTO(Artist artist) {
        return PendingArtistDTO.of(
                artist.getId(),
                artist.getStageName(),
                artist.getApproveStatus(),
                artist.getCreatedAt(),
                toGenres(artist.getGenres()),
                artist.getProfileImageUrl()
        );
    }

    public static GetPendingArtistDetailResponseDTO toPendingArtistDetailResponseDTO(Artist artist) {
        return GetPendingArtistDetailResponseDTO.of(
                artist.getId(),
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

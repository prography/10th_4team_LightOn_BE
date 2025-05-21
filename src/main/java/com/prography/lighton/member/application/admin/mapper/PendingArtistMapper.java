package com.prography.lighton.member.application.admin.mapper;

import com.prography.lighton.artist.domain.entity.Artist;
import com.prography.lighton.artist.domain.entity.ArtistGenre;
import com.prography.lighton.genre.domain.entity.Genre;
import com.prography.lighton.member.presentation.dto.response.GetPendingArtistDetailResponseDTO;
import java.util.List;

public class PendingArtistMapper {

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

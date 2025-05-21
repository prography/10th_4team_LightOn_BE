package com.prography.lighton.artist.admin.application.mapper;

import com.prography.lighton.artist.admin.presentation.GetArtistApplicationDetailResponseDTO;
import com.prography.lighton.artist.admin.presentation.GetArtistApplicationListResponseDTO.ArtistApplicationDTO;
import com.prography.lighton.artist.common.domain.entity.Artist;
import com.prography.lighton.artist.common.domain.entity.ArtistGenre;
import com.prography.lighton.genre.domain.entity.Genre;
import com.prography.lighton.genre.infrastructure.cache.GenreCache;
import com.prography.lighton.region.infrastructure.cache.RegionCache;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PendingArtistMapper {

    private final GenreCache genreCache;
    private final RegionCache regionCache;

    public ArtistApplicationDTO toPendingArtistDTO(Artist artist) {
        return ArtistApplicationDTO.of(
                artist.getId(),
                artist.getStageName(),
                artist.getApproveStatus(),
                artist.getCreatedAt(),
                toGenres(artist.getGenres()),
                artist.getProfileImageUrl()
        );
    }

    public GetArtistApplicationDetailResponseDTO toPendingArtistDetailResponseDTO(Artist artist) {
        return GetArtistApplicationDetailResponseDTO.of(
                artist.getId(),
                artist.getApproveStatus(),
                artist.getStageName(),
                artist.getDescription(),
                regionCache.getRegionCodeByInfo(artist.getActivityLocation()),
                toRegionName(artist),
                toGenres(artist.getGenres()),
                artist.getHistory().getBio(),
                artist.getHistory().getActivityImages().toList(),
                artist.getProofUrl()
        );
    }

    private static String toRegionName(Artist artist) {
        return artist.getActivityLocation().getRegion().getName()
                + " " + artist.getActivityLocation().getSubRegion().getName();
    }

    private List<String> toGenres(List<ArtistGenre> artistGenres) {
        List<Long> genreIds = artistGenres.stream()
                .map(ag -> ag.getGenre().getId()) // Lazy X, 프록시에서 ID만 추출
                .toList();

        return genreCache.getGenresOrThrow(genreIds).stream()
                .map(Genre::getName)
                .toList();
    }

}

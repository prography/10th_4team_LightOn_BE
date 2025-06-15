package com.prography.lighton.performance.admin.application.mapper;

import com.prography.lighton.genre.domain.entity.Genre;
import com.prography.lighton.genre.infrastructure.cache.GenreCache;
import com.prography.lighton.performance.admin.presentation.dto.response.GetPerformanceApplicationDetailResponseDTO;
import com.prography.lighton.performance.admin.presentation.dto.response.GetPerformanceApplicationDetailResponseDTO.PerformanceArtistDTO;
import com.prography.lighton.performance.admin.presentation.dto.response.GetPerformanceApplicationListResponseDTO.PerformanceApplicationDTO;
import com.prography.lighton.performance.common.domain.entity.Performance;
import com.prography.lighton.performance.common.domain.entity.association.PerformanceArtist;
import com.prography.lighton.performance.common.domain.entity.association.PerformanceGenre;
import com.prography.lighton.region.infrastructure.cache.RegionCache;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PendingPerformanceMapper {

    private final GenreCache genreCache;
    private final RegionCache regionCache;

    private static String toRegionName(Performance performance) {
        return performance.getLocation().getRegion().getRegion().getName()
                + " " + performance.getLocation().getRegion().getSubRegion().getName();
    }

    public PerformanceApplicationDTO toPendingPerformanceDTO(Performance performance) {
        return PerformanceApplicationDTO.of(
                performance.getId(),
                performance.getInfo().getPosterUrl(),
                performance.getInfo().getTitle(),
                toGenres(performance.getGenres()),
                performance.getSchedule(),
                regionCache.getRegionCodeByInfo(performance.getLocation().getRegion()),
                toRegionName(performance),
                performance.getApproveStatus()
        );
    }

    public GetPerformanceApplicationDetailResponseDTO toPendingPerformanceDetailResponseDTO(
            Performance performance) {
        List<PerformanceArtistDTO> artistDTOs = performance.getArtists().stream()
                .map(PerformanceArtist::getArtist)
                .map(artist -> PerformanceArtistDTO.of(
                        artist.getId(),
                        artist.getStageName(),
                        artist.getDescription()))
                .toList();

        return GetPerformanceApplicationDetailResponseDTO.of(
                performance.getId(),
                performance.getInfo(),
                artistDTOs,
                toGenres(performance.getGenres()),
                performance.getSchedule(),
                regionCache.getRegionCodeByInfo(performance.getLocation().getRegion()),
                toRegionName(performance),
                performance.getType(),
                performance.getSeats(),
                performance.getProofUrl()
        );
    }

    private List<String> toGenres(List<PerformanceGenre> performanceGenres) {
        List<Long> genreIds = performanceGenres.stream()
                .map(ag -> ag.getGenre().getId())
                .toList();

        return genreCache.getGenresOrThrow(genreIds).stream()
                .map(Genre::getName)
                .toList();
    }
}

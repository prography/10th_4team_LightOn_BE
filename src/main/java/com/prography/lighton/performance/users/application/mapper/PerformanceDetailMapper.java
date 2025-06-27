package com.prography.lighton.performance.users.application.mapper;

import com.prography.lighton.genre.domain.entity.Genre;
import com.prography.lighton.genre.infrastructure.cache.GenreCache;
import com.prography.lighton.performance.common.domain.entity.Performance;
import com.prography.lighton.performance.common.domain.entity.association.PerformanceArtist;
import com.prography.lighton.performance.common.domain.entity.association.PerformanceGenre;
import com.prography.lighton.performance.users.presentation.dto.response.GetPerformanceDetailResponseDTO;
import com.prography.lighton.performance.users.presentation.dto.response.GetPerformanceDetailResponseDTO.PerformanceArtistDTO;
import com.prography.lighton.region.infrastructure.cache.RegionCache;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PerformanceDetailMapper {

    private final GenreCache genreCache;
    private final RegionCache regionCache;

    public GetPerformanceDetailResponseDTO toPerformanceDetailResponseDTO(
            Performance performance) {
        List<PerformanceArtistDTO> artistDTOs = performance.getArtists().stream()
                .map(PerformanceArtist::getArtist)
                .map(artist -> PerformanceArtistDTO.of(
                        artist.getId(),
                        artist.getStageName(),
                        artist.getDescription()))
                .toList();

        return GetPerformanceDetailResponseDTO.of(
                performance.getId(),
                performance.getInfo(),
                artistDTOs,
                toGenres(performance.getGenres()),
                performance.getSchedule(),
                regionCache.getRegionCodeByInfo(performance.getLocation().getRegion()),
                toRegionName(performance),
                performance.getType(),
                performance.getSeats(),
                performance.getTotalSeatsCount(),
                performance.getBookedSeatCount(),
                performance.getProofUrl()
        );
    }

    private String toRegionName(Performance performance) {
        return performance.getLocation().getRegion().getRegion().getName()
                + " " + performance.getLocation().getRegion().getSubRegion().getName();
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

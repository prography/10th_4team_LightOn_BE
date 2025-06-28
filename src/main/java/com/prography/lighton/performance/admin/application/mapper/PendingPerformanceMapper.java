package com.prography.lighton.performance.admin.application.mapper;

import com.prography.lighton.genre.domain.entity.Genre;
import com.prography.lighton.genre.infrastructure.cache.GenreCache;
import com.prography.lighton.performance.admin.presentation.dto.response.GetPerformanceApplicationDetailResponseDTO;
import com.prography.lighton.performance.admin.presentation.dto.response.GetPerformanceApplicationDetailResponseDTO.ArtistDTO;
import com.prography.lighton.performance.admin.presentation.dto.response.GetPerformanceRequestsListResponseDTO.PerformanceRequestDTO;
import com.prography.lighton.performance.common.domain.entity.Busking;
import com.prography.lighton.performance.common.domain.entity.Performance;
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

    public PerformanceRequestDTO toPendingPerformanceDTO(Performance performance) {
        return PerformanceRequestDTO.of(
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

    public GetPerformanceApplicationDetailResponseDTO toDetailDTO(Performance performance) {
        if (performance instanceof Busking busking) {
            return toBuskingDTO(busking);
        }
        return toConcertDTO(performance);
    }

    private GetPerformanceApplicationDetailResponseDTO toConcertDTO(Performance p) {
        return GetPerformanceApplicationDetailResponseDTO.builder()
                .id(p.getId())
                .info(p.getInfo())
                .artists(
                        p.getArtists().stream()
                                .map(pa -> ArtistDTO.of(pa.getArtist().getId(),
                                        pa.getArtist().getStageName(),
                                        pa.getArtist().getDescription()))
                                .toList())
                .genres(toGenres(p.getGenres()))
                .schedule(p.getSchedule())
                .regionCode(regionCache.getRegionCodeByInfo(p.getLocation().getRegion()))
                .regionName(toRegionName(p))
                .type(p.getType())
                .seats(p.getSeats())
                .proofUrl(p.getProofUrl())
                .build();
    }

    private GetPerformanceApplicationDetailResponseDTO toBuskingDTO(Busking b) {
        GetPerformanceApplicationDetailResponseDTO.ArtistDTO artistDto;

        if (b.getArtists().isEmpty()) {
            artistDto = ArtistDTO.of(null, b.getArtistName(), b.getArtistDescription());
        } else {
            var a = b.getArtists().get(0).getArtist();
            artistDto = ArtistDTO.of(a.getId(), a.getStageName(), a.getDescription());
        }

        return GetPerformanceApplicationDetailResponseDTO.builder()
                .id(b.getId())
                .info(b.getInfo())
                .artists(List.of(artistDto))
                .genres(toGenres(b.getGenres()))
                .schedule(b.getSchedule())
                .regionCode(regionCache.getRegionCodeByInfo(b.getLocation().getRegion()))
                .regionName(toRegionName(b))
                .type(b.getType())
                .seats(b.getSeats())
                .proofUrl(b.getProofUrl())
                .build();
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

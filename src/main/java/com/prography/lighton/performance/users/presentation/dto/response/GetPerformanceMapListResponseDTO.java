package com.prography.lighton.performance.users.presentation.dto.response;

import com.prography.lighton.genre.domain.entity.Genre;
import com.prography.lighton.performance.common.domain.entity.Performance;
import com.prography.lighton.performance.common.domain.entity.association.PerformanceGenre;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record GetPerformanceMapListResponseDTO(List<PerformanceMapDTO> performanceMapList) {


    public static GetPerformanceMapListResponseDTO from(List<Performance> performances) {
        List<PerformanceMapDTO> performanceMapList = performances.stream()
                .map(PerformanceMapDTO::from)
                .toList();
        return new GetPerformanceMapListResponseDTO(performanceMapList);
    }

    public record PerformanceMapDTO(
            Long id,
            String posterUrl,
            String title,
            List<String> artistNames,
            Double latitude,
            Double longitude,
            LocalDate startDate,
            LocalTime startTime,
            LocalDate endDate,
            LocalTime endTime,
            String address,
            List<String> genres

    ) {

        public static PerformanceMapDTO from(Performance performance) {
            return new PerformanceMapDTO(
                    performance.getId(),
                    performance.getInfo().getPosterUrl(),
                    performance.getInfo().getTitle(),
                    performance.getArtists().stream()
                            .map(artist -> artist.getArtist().getStageName())
                            .toList(),
                    performance.getLocation().getLatitude(),
                    performance.getLocation().getLongitude(),
                    performance.getSchedule().getStartDate(),
                    performance.getSchedule().getStartTime(),
                    performance.getSchedule().getEndDate(),
                    performance.getSchedule().getEndTime(),
                    performance.getInfo().getPlace(),
                    performance.getGenres().stream()
                            .map(PerformanceGenre::getGenre)
                            .map(Genre::getName)
                            .toList()
            );
        }


    }
}

package com.prography.lighton.performance.domain.entity;

import com.prography.lighton.common.BaseEntity;
import com.prography.lighton.genre.domain.entity.Genre;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceGenre extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Performance performance;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Genre genre;

    public static List<PerformanceGenre> createListFor(Performance performance, List<Genre> genres) {
        return genres.stream()
                .map(genre -> createFor(performance, genre))
                .toList();
    }

    private static PerformanceGenre createFor(Performance performance, Genre genre) {
        return new PerformanceGenre(performance, genre);
    }
}

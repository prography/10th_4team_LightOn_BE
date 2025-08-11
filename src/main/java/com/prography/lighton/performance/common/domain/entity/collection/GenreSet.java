package com.prography.lighton.performance.common.domain.entity.collection;

import com.prography.lighton.genre.domain.entity.Genre;
import com.prography.lighton.performance.common.domain.entity.Performance;
import com.prography.lighton.performance.common.domain.entity.association.PerformanceGenre;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GenreSet {

    public static void updateGenres(Performance owner,
                                    List<PerformanceGenre> current,
                                    List<Genre> incoming) {
        Set<Long> newIds = incoming.stream().map(Genre::getId).collect(Collectors.toSet());
        removeAbsent(current, newIds);

        Set<Long> existingIds = current.stream()
                .map(pg -> pg.getGenre().getId())
                .collect(Collectors.toSet());
        addMissing(owner, current, incoming, existingIds);
    }

    private static void removeAbsent(List<PerformanceGenre> current, Set<Long> keepIds) {
        current.removeIf(pg -> !keepIds.contains(pg.getGenre().getId()));
    }

    private static void addMissing(Performance owner,
                                   List<PerformanceGenre> current,
                                   List<Genre> incoming,
                                   Set<Long> existingIds) {
        List<Genre> toAdd = incoming.stream()
                .filter(g -> !existingIds.contains(g.getId()))
                .toList();
        if (!toAdd.isEmpty()) {
            current.addAll(PerformanceGenre.createListFor(owner, toAdd));
        }
    }

}

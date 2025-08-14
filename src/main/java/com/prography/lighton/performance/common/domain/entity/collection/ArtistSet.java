package com.prography.lighton.performance.common.domain.entity.collection;

import static java.util.function.UnaryOperator.identity;
import static java.util.stream.Collectors.toMap;

import com.prography.lighton.artist.common.domain.entity.Artist;
import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.performance.common.domain.entity.Performance;
import com.prography.lighton.performance.common.domain.entity.association.PerformanceArtist;
import com.prography.lighton.performance.common.domain.exception.MasterArtistCannotBeRemovedException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ArtistSet {

    public static void updateArtists(Performance owner,
                                     List<PerformanceArtist> current,
                                     List<Artist> incoming,
                                     Member performer) {
        validateNotRemovingMaster(incoming, performer);

        Set<Long> newArtistIds = incoming.stream()
                .map(Artist::getId)
                .collect(Collectors.toSet());
        removeAbsent(current, newArtistIds);

        Set<Long> existingArtistIds = current.stream()
                .map(pa -> pa.getArtist().getId())
                .collect(Collectors.toSet());
        addMissing(owner, current, incoming, existingArtistIds);
    }

    private static void validateNotRemovingMaster(List<Artist> incoming, Member performer) {
        boolean masterPresent = incoming.stream()
                .anyMatch(artist -> artist.getMember().getId().equals(performer.getId()));
        if (!masterPresent) {
            throw new MasterArtistCannotBeRemovedException();
        }
    }

    private static void removeAbsent(List<PerformanceArtist> current, Set<Long> keepIds) {
        current.removeIf(pa -> !keepIds.contains(pa.getArtist().getId()));
    }

    private static void addMissing(Performance owner,
                                   List<PerformanceArtist> current,
                                   List<Artist> incoming,
                                   Set<Long> existingIds) {
        Map<Long, Artist> incomingById = incoming.stream()
                .collect(toMap(Artist::getId, identity(), (l, r) -> l));

        List<Artist> toAdd = incomingById.values().stream()
                .filter(a -> !existingIds.contains(a.getId()))
                .toList();
        if (!toAdd.isEmpty()) {
            current.addAll(PerformanceArtist.createListFor(owner, toAdd));
        }
    }

}

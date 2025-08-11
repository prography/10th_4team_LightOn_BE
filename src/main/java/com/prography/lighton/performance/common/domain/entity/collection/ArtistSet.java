package com.prography.lighton.performance.common.domain.entity.collection;

import com.prography.lighton.artist.common.domain.entity.Artist;
import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.performance.common.domain.entity.Performance;
import com.prography.lighton.performance.common.domain.entity.association.PerformanceArtist;
import com.prography.lighton.performance.common.domain.exception.MasterArtistCannotBeRemovedException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ArtistSet {

    public static void updateArtists(Performance owner,
                                     List<PerformanceArtist> current,
                                     List<Artist> incoming,
                                     Member performer) {
        validateNotRemovingMaster(incoming, performer);

        Set<Long> newArtistIds = incoming.stream()
                .map(Artist::getId)
                .collect(Collectors.toSet());

        current.removeIf(pa -> !newArtistIds.contains(pa.getArtist().getId()));

        Set<Long> existingArtistIds = current.stream()
                .map(pa -> pa.getArtist().getId())
                .collect(Collectors.toSet());

        List<Artist> artistsToAdd = incoming.stream()
                .filter(a -> !existingArtistIds.contains(a.getId()))
                .toList();

        current.addAll(PerformanceArtist.createListFor(owner, artistsToAdd));
    }

    private static void validateNotRemovingMaster(List<Artist> incoming, Member performer) {
        boolean masterPresent = incoming.stream()
                .anyMatch(artist -> artist.getMember().getId().equals(performer.getId()));
        if (!masterPresent) {
            throw new MasterArtistCannotBeRemovedException();
        }
    }

}

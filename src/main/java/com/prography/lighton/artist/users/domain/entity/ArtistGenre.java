package com.prography.lighton.artist.users.domain.entity;

import com.prography.lighton.common.domain.BaseEntity;
import com.prography.lighton.genre.domain.entity.Genre;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ArtistGenre extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Artist artist;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Genre genre;

    public static List<ArtistGenre> createListFor(Artist artist, List<Genre> genres) {
        return genres.stream()
                .map(genre -> createFor(artist, genre))
                .toList();
    }

    private static ArtistGenre createFor(Artist artist, Genre genre) {
        return new ArtistGenre(artist, genre);
    }
}

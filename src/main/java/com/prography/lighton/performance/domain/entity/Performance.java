package com.prography.lighton.performance.domain.entity;

import com.prography.lighton.artist.domain.entity.Artist;
import com.prography.lighton.common.BaseEntity;
import com.prography.lighton.genre.domain.entity.Genre;
import com.prography.lighton.performance.domain.entity.association.PerformanceArtist;
import com.prography.lighton.performance.domain.entity.enums.Seat;
import com.prography.lighton.performance.domain.entity.enums.Type;
import com.prography.lighton.performance.domain.entity.vo.Info;
import com.prography.lighton.performance.domain.entity.vo.Location;
import com.prography.lighton.performance.domain.entity.vo.Payment;
import com.prography.lighton.performance.domain.entity.vo.Schedule;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SQLDelete(sql = "UPDATE performance SET status = false WHERE id = ?")
@SQLRestriction("status = true")
public class Performance extends BaseEntity {

    @OneToMany(mappedBy = "performance")
    @NotEmpty
    private List<PerformanceArtist> artists = new ArrayList<>();

    @Embedded
    private Info info;

    @Embedded
    private Schedule schedule;

    @Embedded
    private Location location;

    @Embedded
    private Payment payment;

    @Enumerated(EnumType.STRING)
    private Type type;

    @ElementCollection(targetClass = Seat.class)
    @CollectionTable(
            name = "performance_seat",
            joinColumns = @JoinColumn(name = "performance_id")
    )
    @Column(name = "seat")
    @Enumerated(EnumType.STRING)
    private List<Seat> seats = new ArrayList<>();

    @Column(nullable = false)
    @ColumnDefault("0")
    private Long viewCount = 0L;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Long likeCount = 0L;

    @OneToMany(mappedBy = "performance", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PerformanceGenre> genres = new ArrayList<>();

    private Performance(
            Info info,
            Schedule schedule,
            Location location,
            Payment payment,
            Type type,
            List<Seat> seats
    ) {
        this.info = info;
        this.schedule = schedule;
        this.location = location;
        this.payment = payment;
        this.type = type;
        this.seats.addAll(seats);
    }

    public static Performance create(
            Artist initialArtist,
            Info info,
            Schedule schedule,
            Location location,
            Payment payment,
            Type type,
            List<Seat> seats,
            List<Genre> genres
    ) {
        Performance perf = new Performance(info, schedule, location, payment, type, seats);
        // 최초 등록 시 단일 아티스트 (수정 가능성 있음)
        perf.artists.add(new PerformanceArtist(initialArtist, perf));
        perf.updateGenres(genres);
        return perf;
    }

    public void update(
            List<Artist> newArtists,
            Info info,
            Schedule schedule,
            Location location,
            Payment payment,
            Type type,
            List<Seat> seats,
            List<Genre> genres
    ) {
        this.info = info;
        this.schedule = schedule;
        this.location = location;
        this.payment = payment;
        this.type = type;
        this.seats.clear();
        this.seats.addAll(seats);

        updateArtists(newArtists);
        updateGenres(genres);
    }

    private void updateArtists(List<Artist> newArtists) {
        Set<Long> newArtistIds = newArtists.stream()
                .map(Artist::getId)
                .collect(Collectors.toSet());

        this.artists.removeIf(pa -> !newArtistIds.contains(pa.getArtist().getId()));

        Set<Long> existingArtistIds = this.artists.stream()
                .map(pa -> pa.getArtist().getId())
                .collect(Collectors.toSet());

        List<Artist> artistsToAdd = newArtists.stream()
                .filter(a -> !existingArtistIds.contains(a.getId()))
                .toList();

        this.artists.addAll(PerformanceArtist.createListFor(this, artistsToAdd));
    }
    
    private void updateGenres(List<Genre> newGenres) {
        Set<Long> newIds = newGenres.stream()
                .map(Genre::getId)
                .collect(Collectors.toSet());

        this.genres.removeIf(pg -> !newIds.contains(pg.getGenre().getId()));

        Set<Long> existingGenreIds = this.genres.stream()
                .map(pg -> pg.getGenre().getId())
                .collect(Collectors.toSet());

        List<Genre> genresToAdd = newGenres.stream()
                .filter(g -> !existingGenreIds.contains(g.getId()))
                .toList();

        this.genres.addAll(PerformanceGenre.createListFor(this, genresToAdd));
    }

}

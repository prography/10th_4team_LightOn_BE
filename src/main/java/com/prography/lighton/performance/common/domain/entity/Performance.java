package com.prography.lighton.performance.common.domain.entity;

import com.prography.lighton.artist.admin.domain.exception.InvalidApproveStatusTransitionException;
import com.prography.lighton.artist.admin.domain.exception.SameApproveStatusException;
import com.prography.lighton.artist.common.domain.entity.Artist;
import com.prography.lighton.artist.common.domain.entity.exception.NotAMasterArtistException;
import com.prography.lighton.common.domain.BaseEntity;
import com.prography.lighton.genre.domain.entity.Genre;
import com.prography.lighton.performance.common.domain.entity.association.PerformanceArtist;
import com.prography.lighton.performance.common.domain.entity.enums.ApproveStatus;
import com.prography.lighton.performance.common.domain.entity.enums.Seat;
import com.prography.lighton.performance.common.domain.entity.enums.Type;
import com.prography.lighton.performance.common.domain.entity.vo.Info;
import com.prography.lighton.performance.common.domain.entity.vo.Location;
import com.prography.lighton.performance.common.domain.entity.vo.Payment;
import com.prography.lighton.performance.common.domain.entity.vo.Schedule;
import com.prography.lighton.performance.common.domain.exception.MasterArtistCannotBeRemovedException;
import com.prography.lighton.performance.common.domain.exception.PerformanceNotApprovedException;
import com.prography.lighton.performance.common.domain.exception.PerformanceUpdateNotAllowedException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "performance_type")
@DiscriminatorValue("CONCERT")
@SQLDelete(sql = "UPDATE performance SET status = false WHERE id = ?")
@SQLRestriction("status = true")
public class Performance extends BaseEntity {

    private static final int UPDATE_DEADLINE_DAYS = 3;
    private static final int CANCEL_DEADLINE_DAYS = 3;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Artist master;

    @OneToMany(mappedBy = "performance")
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(20) default 'PENDING'")
    private ApproveStatus approveStatus = ApproveStatus.PENDING;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Long viewCount = 0L;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Long likeCount = 0L;

    @OneToMany(mappedBy = "performance", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PerformanceGenre> genres = new ArrayList<>();

    @Column(nullable = false)
    private String proofUrl;

    @Column(nullable = false)
    @ColumnDefault("false")
    private boolean canceled = false;

    private Performance(
            Artist master,
            Info info,
            Schedule schedule,
            Location location,
            Payment payment,
            Type type,
            List<Seat> seats,
            String proofUrl
    ) {
        this.master = master;
        this.info = info;
        this.schedule = schedule;
        this.location = location;
        this.payment = payment;
        this.type = type;
        this.proofUrl = proofUrl;
        this.seats.addAll(seats);
    }

    public static Performance create(
            Artist initialArtist,
            List<Artist> artists,
            Info info,
            Schedule schedule,
            Location location,
            Payment payment,
            Type type,
            List<Seat> seats,
            List<Genre> genres,
            String proofUrl
    ) {
        Performance perf = new Performance(initialArtist, info, schedule, location, payment, type, seats, proofUrl);
        perf.updateArtists(artists);
        perf.updateGenres(genres);
        return perf;
    }

    protected void initCommonFields(
            Info info,
            Schedule schedule,
            Location location,
            Payment payment,
            Type type,
            List<Seat> seats,
            String proofUrl,
            List<Genre> genres
    ) {
        this.info = info;
        this.schedule = schedule;
        this.location = location;
        this.payment = payment;
        this.type = type;
        this.seats.clear();
        this.seats.addAll(seats);
        this.proofUrl = proofUrl;
        updateGenres(genres);
    }


    public void update(
            Artist artist,
            List<Artist> newArtists,
            Info info,
            Schedule schedule,
            Location location,
            Payment payment,
            List<Seat> seats,
            List<Genre> genres,
            String proofUrl
    ) {
        validateMasterArtist(artist);
        validateWithinAllowedPeriod(UPDATE_DEADLINE_DAYS);

        this.info = info;
        this.schedule = schedule;
        this.location = location;
        this.payment = payment;
        this.seats.clear();
        this.seats.addAll(seats);

        if (!this.proofUrl.equals(proofUrl) && !StringUtils.isEmpty(proofUrl)) {
            this.proofUrl = proofUrl;
        }

        updateArtists(newArtists);
        updateGenres(genres);
    }

    public void validateMasterArtist(Artist artist) {
        if (!master.equals(artist)) {
            throw new NotAMasterArtistException();
        }
    }

    private void updateArtists(List<Artist> newArtists) {
        validateNotRemovingMaster(newArtists);

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

    private void validateNotRemovingMaster(List<Artist> newArtists) {
        boolean isMasterPresent = newArtists.stream()
                .anyMatch(artist -> artist.getId().equals(this.master.getId()));

        if (!isMasterPresent) {
            throw new MasterArtistCannotBeRemovedException();
        }
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

    private void validateWithinAllowedPeriod(int daysBeforePerformance) {
        LocalDate today = LocalDate.now();
        LocalDate updateDeadline = this.schedule.getStartDate().minusDays(daysBeforePerformance);

        if (today.isAfter(updateDeadline)) {
            throw new PerformanceUpdateNotAllowedException();
        }
    }

    public void validateApproved() {
        if (this.approveStatus != ApproveStatus.APPROVED) {
            throw new PerformanceNotApprovedException();
        }
    }

    public void cancel(Artist artist) {
        validateMasterArtist(artist);
        validateWithinAllowedPeriod(CANCEL_DEADLINE_DAYS);
        if (this.canceled) {
            throw new IllegalStateException("이미 취소된 공연입니다.");
        }
        this.canceled = true;
    }

    public void managePerformanceApplication(ApproveStatus targetStatus) {
        if (this.approveStatus == targetStatus) {
            throw new SameApproveStatusException("동일한 상태로는 변경할 수 없습니다.");
        }

        if (this.approveStatus == ApproveStatus.PENDING) {
            if (targetStatus == ApproveStatus.APPROVED || targetStatus == ApproveStatus.REJECTED) {
                this.approveStatus = targetStatus;
                return;
            }
        }

        if (this.approveStatus == ApproveStatus.APPROVED && targetStatus == ApproveStatus.PENDING) {
            this.approveStatus = targetStatus;
            return;
        }

        throw new InvalidApproveStatusTransitionException("현재 상태에서는 해당 상태로 변경할 수 없습니다.");
    }
}

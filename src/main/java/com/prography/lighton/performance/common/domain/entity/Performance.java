package com.prography.lighton.performance.common.domain.entity;

import static jakarta.persistence.FetchType.LAZY;

import com.prography.lighton.artist.common.domain.entity.Artist;
import com.prography.lighton.common.domain.BaseEntity;
import com.prography.lighton.common.domain.DomainValidator;
import com.prography.lighton.genre.domain.entity.Genre;
import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.performance.common.domain.entity.association.PerformanceArtist;
import com.prography.lighton.performance.common.domain.entity.association.PerformanceGenre;
import com.prography.lighton.performance.common.domain.entity.collection.ArtistSet;
import com.prography.lighton.performance.common.domain.entity.collection.GenreSet;
import com.prography.lighton.performance.common.domain.entity.enums.ApproveStatus;
import com.prography.lighton.performance.common.domain.entity.enums.Seat;
import com.prography.lighton.performance.common.domain.entity.enums.Type;
import com.prography.lighton.performance.common.domain.entity.policy.ApprovalPolicy;
import com.prography.lighton.performance.common.domain.entity.vo.Info;
import com.prography.lighton.performance.common.domain.entity.vo.Location;
import com.prography.lighton.performance.common.domain.entity.vo.Payment;
import com.prography.lighton.performance.common.domain.entity.vo.Schedule;
import com.prography.lighton.performance.common.domain.entity.vo.SeatInventory;
import com.prography.lighton.performance.common.domain.exception.NotAuthorizedPerformanceException;
import com.prography.lighton.performance.common.domain.exception.NotAuthorizedPerformanceRequestException;
import com.prography.lighton.performance.common.domain.exception.PerformanceNotApprovedException;
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
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    private static final int CANCEL_DEADLINE_DAYS = 1;

    @ManyToOne(fetch = LAZY, optional = false)
    private Member performer;

    @OneToMany(mappedBy = "performance", cascade = CascadeType.ALL, orphanRemoval = true)
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

    @Embedded
    private SeatInventory seatInventory;

    @OneToMany(mappedBy = "performance", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PerformanceGenre> genres = new ArrayList<>();

    @Column(nullable = false)
    private String proofUrl;

    @Column(nullable = false)
    @ColumnDefault("false")
    private boolean canceled = false;

    private LocalDateTime approvedAt;

    private Performance(
            Member performer,
            Info info,
            Schedule schedule,
            Location location,
            Payment payment,
            Type type,
            List<Seat> seats,
            String proofUrl,
            SeatInventory seatInventory
    ) {
        this.performer = performer;
        this.info = info;
        this.schedule = schedule;
        this.location = location;
        this.payment = payment;
        this.type = type;
        this.proofUrl = proofUrl;
        this.seats.addAll(seats);
        this.seatInventory = seatInventory;
    }

    /* ---------------------------- 공연 생성 관련 메서드 ---------------------------- */
    public static Performance create(
            Member performer,
            List<Artist> artists,
            Info info,
            Schedule schedule,
            Location location,
            Payment payment,
            List<Seat> seats,
            List<Genre> genres,
            String proofUrl,
            int totalSeatsCount
    ) {
        Performance perf = new Performance(performer, info, schedule, location, payment, Type.CONCERT, seats, proofUrl,
                SeatInventory.ofTotal(totalSeatsCount));
        perf.updateArtists(artists);
        perf.updateGenres(genres);
        return perf;
    }

    /* ---------------------------- 공연 수정 관련 메서드 ---------------------------- */
    public void update(
            Member performer,
            List<Artist> newArtists,
            Info info,
            Schedule schedule,
            Location location,
            Payment payment,
            List<Seat> seats,
            List<Genre> genres,
            String proofUrl,
            int totalSeatsCount
    ) {
        validatePerformer(performer);
        ensureUpdatableWindow();
        DomainValidator.requireNonBlank(proofUrl);

        this.info = info;
        this.schedule = schedule;
        this.location = location;
        this.payment = payment;
        this.seats.clear();
        this.seats.addAll(seats);
        this.proofUrl = proofUrl;
        seatInventory.updateSeat(totalSeatsCount);

        updateArtists(newArtists);
        updateGenres(genres);
    }

    protected void initCommonFields(
            Member performer,
            Info info,
            Schedule schedule,
            Location location,
            Payment payment,
            Type type,
            Seat seat,
            String proofUrl,
            List<Genre> genres,
            SeatInventory seatInventory
    ) {
        this.performer = performer;
        this.info = info;
        this.schedule = schedule;
        this.location = location;
        this.payment = payment;
        this.type = type;
        this.seats.clear();
        this.seats.add(seat);
        this.proofUrl = proofUrl;
        this.seatInventory = seatInventory;
        updateGenres(genres);
    }

    public void cancel(Member member) {
        validatePerformer(member);
        ensureCancelableWindow();
        if (this.canceled) {
            throw new IllegalStateException("이미 취소된 공연입니다.");
        }
        this.canceled = true;
    }

    protected int updateDeadlineDays() {
        return UPDATE_DEADLINE_DAYS;
    }

    protected int cancelDeadlineDays() {
        return CANCEL_DEADLINE_DAYS;
    }

    protected final void ensureUpdatableWindow() {
        getSchedule().validateWithinAllowedPeriod(updateDeadlineDays());
    }

    protected final void ensureCancelableWindow() {
        getSchedule().validateWithinAllowedPeriod(cancelDeadlineDays());
    }

    protected void updateArtists(List<Artist> newArtists) {
        ArtistSet.updateArtists(this, this.artists, newArtists, this.getPerformer());
    }

    private void updateGenres(List<Genre> newGenres) {
        GenreSet.updateGenres(this, this.genres, newGenres);
    }

    /* ---------------------------- 공연 상태 관련 메서드 ---------------------------- */
    public void managePerformanceApplication(ApproveStatus targetStatus) {
        ApproveStatus prev = this.approveStatus;
        ApproveStatus next = ApprovalPolicy.next(prev, targetStatus);

        this.approveStatus = next;
        if (ApprovalPolicy.requiresApprovedAt(prev, next)) {
            this.approvedAt = LocalDateTime.now();
        }
    }

    /* ---------------------------- 공연 신청 관련 메서드 ---------------------------- */
    public PerformanceRequest createRequest(int applySeats, Member member) {
        validateApproved();
        seatInventory.reserve(applySeats);
        return PerformanceRequest.of(member, this, applySeats, payment.getFee());
    }

    public void cancelRequest(PerformanceRequest request, Member member) {
        if (!request.getMember().getId().equals(member.getId())) {
            throw new NotAuthorizedPerformanceRequestException();
        }
        seatInventory.cancel(request.getRequestedSeats());
    }

    /* ---------------------------- 공연 좋아요 관련 메서드 ---------------------------- */
    public void increaseLike() {
        this.likeCount++;
    }

    public void decreaseLike() {
        if (this.likeCount > 0) {
            this.likeCount--;
        }
    }


    /* ---------------------------- 공연 검증 관련 메서드 ---------------------------- */
    protected void validatePerformer(Member member) {
        if (!performer.equals(member)) {
            throw new NotAuthorizedPerformanceException();
        }
    }

    public void validateApproved() {
        if (this.approveStatus != ApproveStatus.APPROVED) {
            throw new PerformanceNotApprovedException();
        }
    }

    public void validateIsManagedBy(Member member) {
        validateApproved();

        if (!this.performer.equals(member)) {
            throw new NotAuthorizedPerformanceException();
        }
    }
}

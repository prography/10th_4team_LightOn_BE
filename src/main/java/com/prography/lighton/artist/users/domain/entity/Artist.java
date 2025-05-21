package com.prography.lighton.artist.users.domain.entity;

import com.prography.lighton.artist.admin.domain.exception.InvalidApproveStatusTransitionException;
import com.prography.lighton.artist.admin.domain.exception.SameApproveStatusException;
import com.prography.lighton.artist.users.domain.entity.enums.ApproveStatus;
import com.prography.lighton.artist.users.domain.entity.exception.ArtistNotApprovedException;
import com.prography.lighton.artist.users.domain.entity.exception.ArtistRegistrationNotAllowedException;
import com.prography.lighton.artist.users.domain.entity.vo.History;
import com.prography.lighton.common.domain.BaseEntity;
import com.prography.lighton.common.domain.DomainValidator;
import com.prography.lighton.common.domain.vo.RegionInfo;
import com.prography.lighton.genre.domain.entity.Genre;
import com.prography.lighton.member.domain.entity.Member;
import com.prography.lighton.performance.domain.entity.association.PerformanceArtist;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@SQLDelete(sql = "UPDATE artist SET status = false WHERE id = ?")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("status = true")
public class Artist extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private Member member;

    @Column(nullable = false)
    private String stageName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(20) default 'PENDING'")
    private ApproveStatus approveStatus = ApproveStatus.PENDING;

    private LocalDateTime requestAt;

    private LocalDateTime approveAt;

    @Column(nullable = false, columnDefinition = "BIGINT DEFAULT 0")
    private Long viewCount = 0L;

    @Column(nullable = false, columnDefinition = "BIGINT DEFAULT 0")
    private Long likeCount = 0L;

    @Column(nullable = false)
    private String description;

    private String profileImageUrl;

    @Embedded
    private RegionInfo activityLocation;

    @Embedded
    private History history;

    @Column(nullable = false)
    private String proofUrl;

    @OneToMany(mappedBy = "artist")
    private List<PerformanceArtist> performances = new ArrayList<>();

    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArtistGenre> genres = new ArrayList<>();

    private Artist(Member member, String stageName, String description, String profileImageUrl,
                   RegionInfo activityLocation, History history,
                   String proofUrl) {
        this.member = member;
        this.stageName = DomainValidator.requireNonBlank(stageName);
        this.description = DomainValidator.requireNonBlank(description);
        this.profileImageUrl = DomainValidator.requireNonBlank(profileImageUrl);
        this.activityLocation = activityLocation;
        this.history = history;
        this.proofUrl = proofUrl;
        this.requestAt = LocalDateTime.now(); // 신청 시각을 현재 시각으로 설정, 임시 설정, 추후 변경 예정
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Artist)) {
            return false;
        }

        Artist artist = (Artist) o;
        return this.getId() != null && this.getId().equals(artist.getId());
    }

    @Override
    public int hashCode() {
        return (getId() != null ? getId().hashCode() : 0);
    }

    public static Artist create(
            Member member,
            String stageName,
            String description,
            String profileImageUrl,
            RegionInfo activityLocation,
            History history,
            String proofUrl,
            List<Genre> genres
    ) {
        Artist artist = new Artist(
                member,
                stageName,
                description,
                profileImageUrl,
                activityLocation,
                history,
                proofUrl
        );

        artist.updateGenres(genres);
        return artist;
    }

    private void updateGenres(List<Genre> newGenres) {
        Set<Long> newGenreIds = newGenres.stream()
                .map(Genre::getId)
                .collect(Collectors.toSet());

        this.genres.removeIf(ag -> !newGenreIds.contains(ag.getGenre().getId()));

        Set<Long> existingGenreIds = this.genres.stream()
                .map(ag -> ag.getGenre().getId())
                .collect(Collectors.toSet());

        List<Genre> genresToAdd = newGenres.stream()
                .filter(g -> !existingGenreIds.contains(g.getId()))
                .toList();

        this.genres.addAll(ArtistGenre.createListFor(this, genresToAdd));
    }

    public void update(
            String stageName,
            String description,
            String profileImageUrl,
            RegionInfo activityLocation,
            History history,
            List<Genre> genres
    ) {
        this.stageName = stageName;
        this.description = description;
        this.profileImageUrl = profileImageUrl;
        this.activityLocation = activityLocation;
        this.history = history;
        this.updateGenres(genres);
    }

    public void isValidRecreatable() {
        if (this.approveStatus != ApproveStatus.REJECTED) {
            throw new ArtistRegistrationNotAllowedException();
        }
    }

    public void isValidApproved() {
        if (this.approveStatus != ApproveStatus.APPROVED) {
            throw new ArtistNotApprovedException();
        }
    }

    public void manageArtistApplication(ApproveStatus targetStatus) {
        if (this.approveStatus == targetStatus) {
            throw new SameApproveStatusException("동일한 상태로는 변경할 수 없습니다.");
        }

        // 예: 상태 전이 허용 규칙
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

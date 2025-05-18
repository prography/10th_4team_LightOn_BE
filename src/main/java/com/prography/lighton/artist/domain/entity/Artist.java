package com.prography.lighton.artist.domain.entity;

import com.prography.lighton.artist.domain.entity.enums.ApproveStatus;
import com.prography.lighton.artist.domain.entity.vo.History;
import com.prography.lighton.common.BaseEntity;
import com.prography.lighton.common.vo.RegionInfo;
import com.prography.lighton.member.domain.entity.Member;
import com.prography.lighton.performance.domain.entity.association.PerformanceArtist;
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

    private LocalDateTime approveAt;

    @Column(nullable = false, columnDefinition = "BIGINT DEFAULT 0")
    private Long viewCount = 0L;

    @Column(nullable = false, columnDefinition = "BIGINT DEFAULT 0")
    private Long likeCount = 0L;

    @Column(nullable = false)
    private String description;

    @Embedded
    private RegionInfo activityLocation;

    @Embedded
    private History history;

    @Column(nullable = false)
    private String proofUrl;

    @OneToMany(mappedBy = "artist")
    private List<PerformanceArtist> performances = new ArrayList<>();

    public Artist(Member member, String stageName, String description, RegionInfo activityLocation, History history,
                  String proofUrl) {
        this.member = member;
        this.stageName = stageName;
        this.description = description;
        this.activityLocation = activityLocation;
        this.history = history;
        this.proofUrl = proofUrl;
    }

    public static Artist create(
            Member member,
            String stageName,
            String description,
            RegionInfo activityLocation,
            History history,
            String proofUrl
    ) {
        return new Artist(
                member,
                stageName,
                description,
                activityLocation,
                history,
                proofUrl
        );
    }
}

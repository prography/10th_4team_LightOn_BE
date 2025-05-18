package com.prography.lighton.artist.domain.entity;

import com.prography.lighton.artist.domain.entity.enums.ApproveStatus;
import com.prography.lighton.artist.domain.entity.vo.ActivityLocation;
import com.prography.lighton.artist.domain.entity.vo.History;
import com.prography.lighton.common.BaseEntity;
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
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE artist SET status = false WHERE id = ?")
@SQLRestriction("status = true")
public class Artist extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private Member member;

    @Column(nullable = false)
    private String stageName;

    @Enumerated(EnumType.STRING)
    private ApproveStatus approveStatus;

    @Column(nullable = false)
    private LocalDateTime approveAt;

    @Column(nullable = false, columnDefinition = "BIGINT DEFAULT 0")
    @ColumnDefault("0")
    private Long viewCount = 0L;

    @Column(nullable = false, columnDefinition = "BIGINT DEFAULT 0")
    @ColumnDefault("0")
    private Long likeCount = 0L;

    @Column(nullable = false)
    private String description;

    @Embedded
    private ActivityLocation activityLocation;

    @Embedded
    private History history;

    @Column(nullable = false)
    private String proofUrl;

    @OneToMany(mappedBy = "artist")
    private List<PerformanceArtist> performances = new ArrayList<>();
}

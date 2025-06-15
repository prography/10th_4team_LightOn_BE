package com.prography.lighton.performance.common.domain.entity;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

import com.prography.lighton.common.domain.BaseEntity;
import com.prography.lighton.member.common.domain.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "performance_like",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_member_performance", columnNames = {"member_id", "performance_id"}
        )
)
@Getter
@NoArgsConstructor(access = PROTECTED)
public class PerformanceLike extends BaseEntity {

    @ManyToOne(fetch = LAZY, optional = false)
    private Member member;

    @ManyToOne(fetch = LAZY, optional = false)
    private Performance performance;

    private PerformanceLike(Member member, Performance performance) {
        this.member = member;
        this.performance = performance;
    }

    public static PerformanceLike of(Member member, Performance p) {
        return new PerformanceLike(member, p);
    }
}


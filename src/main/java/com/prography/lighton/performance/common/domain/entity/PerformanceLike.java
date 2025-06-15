package com.prography.lighton.performance.common.domain.entity;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

import com.prography.lighton.common.domain.BaseEntity;
import com.prography.lighton.member.common.domain.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
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


package com.prography.lighton.performance.common.domain.entity;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import com.prography.lighton.common.domain.BaseEntity;
import com.prography.lighton.member.common.domain.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(
        name = "performance_like",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_member_performance", columnNames = {"member_id", "performance_id"}
        )
)
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@SQLDelete(sql = "UPDATE performance_like SET status = false WHERE id = ?")
@SQLRestriction("status = true")
public class PerformanceLike extends BaseEntity {

    @ManyToOne(fetch = LAZY, optional = false)
    private Member member;

    @ManyToOne(fetch = LAZY, optional = false)
    private Performance performance;

    @Column(nullable = false)
    @ColumnDefault("true")
    private boolean liked = true;

    public static PerformanceLike of(Member member, Performance performance, boolean liked) {
        return new PerformanceLike(member, performance, liked);
    }

    public void like() {
        this.liked = true;
    }

    public void unlike() {
        this.liked = false;
    }
}


package com.prography.lighton.performance.common.domain.entity;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import com.prography.lighton.common.domain.BaseEntity;
import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.performance.common.domain.entity.enums.RequestStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@SQLDelete(sql = "UPDATE performance_request SET status = false WHERE id = ?")
@SQLRestriction("status = true")
public class PerformanceRequest extends BaseEntity {

    @ManyToOne(fetch = LAZY, optional = false)
    private Member member;

    @ManyToOne(fetch = LAZY, optional = false)
    private Performance performance;

    @Enumerated(EnumType.STRING)
    private RequestStatus requestStatus;

    private LocalDateTime requestedAt;

    private Integer requestedSeats;

    private Integer fee;

    public static PerformanceRequest of(Member member, Performance performance, Integer requestedSeats, Integer fee) {
        return new PerformanceRequest(member, performance, RequestStatus.PENDING, LocalDateTime.now(), requestedSeats,
                fee);
    }

    public void updateRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public void inactivate() {
        this.requestStatus = RequestStatus.REJECTED;
    }


}
